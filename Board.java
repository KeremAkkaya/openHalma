import java.util.*;
import java.io.*;

/**
 * Abstract class Board - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Board
{
    protected FIELD_VALUE board[][];

    protected int dimension; //size of matrix representing the board
    public Board() { //called before loading board via serialization

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
        return getPosition(p.x, p.y);
    }

    public FIELD_VALUE getPosition(int x, int y) {
        if ((x < dimension) && (y < dimension) && (x >= 0) && (y >= 0)) {
            return board[x][y];
        }
        return FIELD_VALUE.INVALID;
    }

    public int setPosition(Position p, FIELD_VALUE val) {
        return setPosition(p.x, p.y, val);
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
    
    public boolean isValidMove(Move move) {
        if (getJumpPositions(move.start, move.player).contains(move.end)) return true;
        return false;
    }

    public String writeToString(){
        String s = "";
        s += dimension + ";";
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s += board[i][j].getVal();
                s += ";";
            }
            s += ";";
        }
        s += ";";
        return s;
    }

    public boolean readFromString(String s) {
        StringBuilder sb = new StringBuilder(s);
        int dimension = Integer.parseInt(Helper.popString(sb));
        if (!Helper.isValue(sb, ";", "invalid format for board1")) return false;
        FIELD_VALUE[][] board = new FIELD_VALUE[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = FIELD_VALUE.getValByInt(Integer.parseInt(Helper.popString(sb)));
                if (!Helper.isValue(sb, ";", "invalid format for board2")) return false;
            }
            if (!Helper.isValue(sb, ";", "invalid format for board3")) return false;
        }
        if (!Helper.isValue(sb, ";", "invalid format for board4")) return false;
        this.dimension = dimension;
        this.board = board;
        return true;
    }

    public boolean equals(Board b) {
        if (b.dimension != this.dimension) return false;

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.board[i][j] != b.board[i][j]) return false;
            }
        }

        return true;
    }
}
