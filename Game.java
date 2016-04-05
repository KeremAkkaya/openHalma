import java.util.LinkedList;
import java.io.*;
/**
 * Write a description of class Game here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Game implements Serializable
{
    private Board board;
    private LinkedList<Move> moves = new LinkedList<>();
    private LinkedList<Move> cachedMoves = new LinkedList<>(); //additional list for 'undo'/'redo' functionality
    private LinkedList<Player> players = new LinkedList<>();
    private Interface iface;

    public Game() {

    }

    public Game(Interface i, Board board) {
        this.iface = i;
        this.iface.setGame(this);
        this.board = board;
    }
    
    public boolean addPlayer(Player p) {
        if (players.size() < board.getMaxPlayers()) {
            if (!players.contains(p)) {
                players.addLast(p);
                return true;
            }
        }
        return false;
    }
    
    public boolean removePlayer(Player p) {
        if (players.contains(p)) {
            players.remove(p);
            return true;
        }
        return false;
    }

    public boolean start() {
        if (players.size() < 2) {
            return false;
        }
        while (!isFinished()) requestMove();
        return true;
    }
    
    public boolean tryMove(Move move) {
        if(board.getJumpPositions(move.start, getNextPlayer()).contains(move.end)) {
            makeMove(move);
            return true;
        }
        return false;
    }

    private void makeMove(Move move) {
        System.out.println(move.toString());
        applyMove(move);
        moves.add(move);
        cachedMoves.clear();
        players.addLast(players.removeFirst());
    }

    private void applyMove(Move move) {
        board.setPosition(move.start, Player.emptyPlayer.getFieldValue());
        board.setPosition(move.end, move.player.getFieldValue());
    }

    public boolean undoMove() {
        if (undoable()) {
            Move m = moves.getLast();
            applyMove(new Move(m.player, m.end, m.start));
            players.addFirst(players.removeLast());
            cachedMoves.addLast(moves.removeLast());
            return true;
        } else {
            return false;
        }
    }

    public boolean redoMove() {
        if (redoable()) {
            applyMove(cachedMoves.getLast());
            players.addLast(players.removeFirst());
            moves.addLast(cachedMoves.removeLast());
            return true;
        } else {
            return false;
        }
    }

    public boolean undoable() {
        return moves.peekFirst() != null;
    }

    public boolean redoable() {
        return cachedMoves.peekFirst() != null;
    }

    public Player getNextPlayer() {
        return players.peekFirst();
    }
    
    public boolean isFinished() {
        return true;
    }

    public void requestMove() {
        Move move;
        do {
            move = players.peekFirst().requestMove(iface, board); //wait til valid turn
        } while (!board.isValidMove(move));
        makeMove(move);
    }
    
    public Board getBoard() {
        return board;
    }
}
