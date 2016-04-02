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
    private LinkedList<Player> players;
    private Interface iface;

    public Game() {

    }

    public Game(Interface i) {
        this.iface = i;
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

    public int undoMove() {
        if (undoable()) {
            Move m = moves.getLast();
            applyMove(new Move(m.player, m.end, m.start));
            players.addFirst(players.pop());
            cachedMoves.push(moves.pop());
            return 0;
        } else {
            return 1;
        }
    }

    public int redoMove() {
        if (redoable()) {
            applyMove(cachedMoves.getLast());
            players.push(players.remove());
            moves.push(cachedMoves.pop());
            return 0;
        } else {
            return 1;
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
            move = players.peekFirst().requestMove(iface); //wait til valid turn
        } while (!board.isValidMove(move));
        makeMove(move);
        players.push(players.remove());
    }
}
