
/**
 * Abstract class Board - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Board
{
    protected FIELD_VALUE board[][];

    public FIELD_VALUE getPosition(int x, int y) {
         if ((x < getDimension()) && (y < getDimension()) && (x >= 0) && (y >= 0)) {
            return board[x][y];
        }
        return FIELD_VALUE.INVALID;
    }

    public int setPosition(int x, int y, FIELD_VALUE val) {
        if ((x < getDimension()) && (y < getDimension()) && (x >= 0) && (y >= 0)) {
            board[x][y] = val;
            return 0;
        }
        return 1;
    }
    
    public abstract void initBoard();

    public abstract String toText();
    
    public abstract int getDimension();
}
