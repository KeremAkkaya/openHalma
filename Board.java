
/**
 * Abstract class Board - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Board
{
    private int dimension;
    private FIELD_VALUE field[][];
    
    public FIELD_VALUE getPosition(int x, int y) {
        if ((x < dimension) && (y < dimension) && (x >= 0) && (y >= 0)) {
            return field[x][y];
        }
        return FIELD_VALUE.INVALID;
    }
    
    public int setPosition(int x, int y, FIELD_VALUE val) {
        if ((x < dimension) && (y < dimension) && (x >= 0) && (y >= 0)) {
            field[x][y] = val;
            return 0;
        }
        return 1;
    }
    
    public abstract void initBoard();
    
    public abstract String toText();
}
