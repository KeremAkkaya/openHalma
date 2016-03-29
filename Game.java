import java.util.LinkedList;
/**
 * Write a description of class Game here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Game
{
    private Board board;
    private LinkedList<Move> moves = new LinkedList<>();
    private LinkedList<Move> cachedMoves = new LinkedList<>(); //additional list for 'undo'/'redo' functionality
    public Game()
    {

    }

    public int makeMove(Move move) {
        //addlast
        //getFirst
        //getlast
        if (board.getJumpPositions(move.start,move.player).contains(move.end)) {
            System.out.println(move.toString());
            moves.add(move);
            cachedMoves.clear();
            return 0;
        }
        return 1; //invalid move
    }

    private void applyMove(Move move) {
        board.setPosition(move.start, FIELD_VALUE.EMPTY);
        board.setPosition(move.end, move.player.getFieldValue());
    }

    public int undoMove() {
        if (undoable()) {
            Move m = moves.getLast();
            applyMove(new Move(m.player, m.end, m.start));
            cachedMoves.push(moves.pop());
            return 0;
        } else {
            return 1;
        }
    }

    public int redoMove() {
        if (redoable()) {
            applyMove(cachedMoves.getLast());
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
}
