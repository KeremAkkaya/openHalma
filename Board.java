import java.util.*;
/**
 * Abstract class Board - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Board
{
    protected FIELD_VALUE board[][];

    public FIELD_VALUE getPosition(Position p) {
         if ((p.x < getDimension()) && (p.y < getDimension()) && (p.x >= 0) && (p.y >= 0)) {
            return board[p.x][p.y];
        }
        return FIELD_VALUE.INVALID;
    }

    public int setPosition(Position p, FIELD_VALUE val) {
        if ((p.x < getDimension()) && (p.y < getDimension()) && (p.x >= 0) && (p.y >= 0)) {
            board[p.x][p.y] = val;
            return 0;
        }
        return 1;
    }
    
    public abstract void initBoard();

    public abstract String toText();
    
    public abstract int getDimension();
    
    public abstract LinkedList<Position> getJumpPositions(Position pos, Player p);
}
