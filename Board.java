import java.util.*;
/**
 * Abstract class Board - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Board// implements java.io.Serializable
{
    protected FIELD_VALUE board[][];

    protected int dimension;
    public Board() {
        System.out.println("need dimension");
    }

    public Board(int dimension) {
        this.dimension = dimension;
        board = new FIELD_VALUE[dimension][dimension];
    }

    public Board(FIELD_VALUE[][] board, int dimension) //constructor used to load a saved game
    {
        this(dimension);
        //board is created empty and then replaced by another one :/ its ok for the moment
        this.board = board;
    }

    public FIELD_VALUE getPosition(Position p) {
        if ((p.x < dimension) && (p.y < dimension) && (p.x >= 0) && (p.y >= 0)) {
            return board[p.x][p.y];
        }
        return FIELD_VALUE.INVALID;
    }

    public FIELD_VALUE getPosition(int x, int y) {
        if ((x < dimension) && (y < dimension) && (x >= 0) && (y >= 0)) {
            return board[x][y];
        }
        return FIELD_VALUE.INVALID;
    }

    public int setPosition(Position p, FIELD_VALUE val) {
        if ((p.x < dimension) && (p.y < dimension) && (p.x >= 0) && (p.y >= 0)) {
            board[p.x][p.y] = val;
            return 0;
        }
        return 1;
    }

    public int setPosition(int x, int y, FIELD_VALUE val) {
        if ((x < dimension) && (y < dimension) && (x >= 0) && (y >= 0)) {
            board[x][y] = val;
            return 0;
        }
        return 1;
    }

    public void initBoard() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = FIELD_VALUE.INVALID;
            }
        }
    }

    public int getDimension() {
        return dimension;
    }

    public abstract String toString();

    public abstract LinkedList<Position> getJumpPositions(Position pos, Player p);
}
