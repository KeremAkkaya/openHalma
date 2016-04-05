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

    private void makeMove(Move move) {
        System.out.println(move.toString());
        applyMove(move);
        moves.add(move);
        cachedMoves.clear();
    }

    private void applyMove(Move move) {
        board.setPosition(move.start, FIELD_VALUE.EMPTY);
        board.setPosition(move.end, move.player.getFieldValue());
    }

    public boolean undoMove() {
        if (undoable()) {
            Move m = moves.getLast();
            applyMove(new Move(m.player, m.end, m.start));
            players.addFirst(players.pop());
            cachedMoves.push(moves.pop());
            return true;
        } else {
            return false;
        }
    }

    public boolean redoMove() {
        if (redoable()) {
            applyMove(cachedMoves.getLast());
            players.push(players.remove());
            moves.push(cachedMoves.pop());
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
        players.push(players.remove());
    }
    
    public Board getBoard() {
        return board;
    }
}
