import java.util.*;
/**
 * Write a description of class StarBoard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StarBoard extends Board
{
    private int[][] signa = new int[][]{ //these are the signs for that indicate the 6 directions in the 2d matrix (the board)
            { 0, 1 },
            { 1, 0 },
            { 0,-1 },
            {-1, 0 },
            { 1,-1 },
            {-1, 1 }
        };
    public StarBoard() {
        
    }    
    
    public StarBoard(int dimension) {
        super(dimension);
    }

    public StarBoard(FIELD_VALUE[][] board, int dimension)
    {
        super(board, dimension);
    }

    public String toString(boolean format) {
        String s = "";
        String c = format ? " " : "";
        if(!format) {
            //             s += "  ";
            //             for (int i = 1; i < dimension * 2; i += 2) {
            //                 s += i < 10 ? "0" + i : i;
            //             }
            //             s += "\n";
        }
        for (int i = 0; i < dimension; i ++) {
            s += i < 10 ? "0" + i : i;
            for (int k = 0; k < i; k++) s += c;
            for (int j = 0; j < dimension; j++) {
                s += board[i][j].getSymbol() + " ";
            }
            s += "\n";
        }
        return s;
    }

    public String toString() {
        return toString(true);
    }

    public LinkedList<Position> getJumpPositions(Position pos, Player p) {
        LinkedList<Position> positions = new LinkedList<>();
        if(!(getPosition(pos).equals(p.getFieldValue()))) {
            //try to move other than player
            //does it make sense to restrict it to the player?
            return positions;
        }
        //add 2 jump moves
        _getJumpPositions(pos.x,pos.y,p.getFieldValue(),positions);
        //add 1 jump moves
        for (int i = 0; i < 6; i++) {
            if (isValidJump(pos.x, pos.y, pos.x + signa[i][0], pos.y + signa[i][1], p.getFieldValue())) {
                positions.add(new Position(pos.x + signa[i][0], pos.y + signa[i][1]));
            }
        }
        return positions;
    }

    private boolean inHexagon(int ax, int ay, int bx, int by, int radius) {
        int diffx = bx - ax, diffy = by - ay;
        for (int i = 0; i < 6; i++) {
            if ((diffx == radius*signa[i][0]) && (diffy == radius*signa[i][1])) return true;
        }
        return false;
    }

    private boolean isValidJump(int ax, int ay, int bx, int by, FIELD_VALUE v) {
        int sigx = (int)Math.signum(bx - ax), sigy = (int)Math.signum(by - ay);
        if ( ( (getPosition(ax, ay) != v) && (getPosition(ax, ay) != FIELD_VALUE.EMPTY) ) || (getPosition(bx, by) != FIELD_VALUE.EMPTY) ) {
            return false;
        }
        if (inHexagon(ax,ay,bx,by,1)) {
            return true;
        }
        if (inHexagon(ax,ay,bx,by,2)) {
            if (getPosition(ax + sigx, ay + sigy).getVal() >= 0) return true;
            return false;
        }
        return false;
    }

    private boolean testPosition(int x, int y, int offsetx, int offsety, FIELD_VALUE v, LinkedList<Position> positions) {
        Position target = new Position(x + offsetx, y + offsety);
        if (positions.contains(target)) return false;
        if (isValidJump(x, y, x + offsetx, y + offsety, v)) {
            positions.add(target);
            _getJumpPositions(x + offsetx, y + offsety, v, positions);
            return true;
        }
        return false;
    }

    private LinkedList<Position> _getJumpPositions(int x, int y, FIELD_VALUE v, LinkedList<Position> positions) {
        //Jumps in 6 directions
        for (int i = 0; i < 6; i++) {
            testPosition(x,y,2*signa[i][0],2*signa[i][1],v,positions);
        }
        return positions;

    }
    
    
    //TODO: make these getJumpPositions WAAAAAAAY more efficient

}