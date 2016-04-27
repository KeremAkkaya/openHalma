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

    public String toString(boolean format) { //setting format to false results in a more debug-style output
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
            if(!format) s += i < 10 ? "0" + i : i;
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

    //returns all valid positions given a player and a (start-)position
    //if p == null, the search is not restricted to the player
    public LinkedList<Position> getJumpPositions(Position pos, Player p) {
        LinkedList<Position> positions = new LinkedList<>();
        FIELD_VALUE fv;
        if (getPosition(pos).getVal() < 0 ) return positions; //ignore start positions that are empty or invalid
        if (p != null) {
            if(!(getPosition(pos).equals(p.getFieldValue()))) {
                //try to move other than player
                return positions;
            }
            fv = p.getFieldValue();
        } else {
            fv = null;
        }
        //this call adds all jumps and chained jumps
        _getJumpPositions(pos.x,pos.y,fv,positions);
        
        for (int i = 0; i < 6; i++) { //this loop checks whether all the positions where the token is only moved one field are valid jumps
            if (isValidJump(pos.x, pos.y, pos.x + signa[i][0], pos.y + signa[i][1], fv)) {
                positions.add(new Position(pos.x + signa[i][0], pos.y + signa[i][1]));
            }
        }
        return positions;
    }

    //returns true if the position given by (bx,by) is in on the hexagon with given radius and center (ax,ay)
    private boolean inHexagon(int ax, int ay, int bx, int by, int radius) {
        int diffx = bx - ax, diffy = by - ay;
        for (int i = 0; i < 6; i++) {
            if ((diffx == radius*signa[i][0]) && (diffy == radius*signa[i][1])) return true;
        }
        return false;
    }

    //returns true if the jump from (ax,ay) to (bx,by) is valid; only works for jumps with distance <=2 (1-field move and unchained jump)
    private boolean isValidJump(int ax, int ay, int bx, int by, FIELD_VALUE v) {
        int sigx = (int)Math.signum(bx - ax), sigy = (int)Math.signum(by - ay);
        if (v == null) { //ignore player
            if ( ( (getPosition(ax, ay) == FIELD_VALUE.INVALID) ) || (getPosition(bx, by) != FIELD_VALUE.EMPTY) ) {
                return false; //...if starting position is invalid or target position is not empty
            }
        } else { //do not ignore player
            if ( ( (getPosition(ax, ay) != v) && (getPosition(ax, ay) != FIELD_VALUE.EMPTY) ) || (getPosition(bx, by) != FIELD_VALUE.EMPTY) ) {
                return false; //...if starting position != playertoken or target position is not empty
            }
        }

        if (inHexagon(ax,ay,bx,by,1)) { //1-field move
            return true;
        }
        if (inHexagon(ax,ay,bx,by,2)) { //2-field jump
            if (getPosition(ax + sigx, ay + sigy).getVal() >= 0) return true; //check whether there is a token to jump over
            return false;
        }
        return false;
    }

    private boolean testPosition(int x, int y, int offsetx, int offsety, FIELD_VALUE v, LinkedList<Position> positions) {
        Position target = new Position(x + offsetx, y + offsety);
        if (positions.contains(target)) return false;
        if (isValidJump(x, y, x + offsetx, y + offsety, v)) {
            positions.add(target);
            _getJumpPositions(x + offsetx, y + offsety, v, positions); //check all possible jumps of next position
            return true;
        }
        return false;
    }

    private List<Position> _getJumpPositions(int x, int y, FIELD_VALUE v, LinkedList<Position> positions) {
        //Jumps in 6 directions
        for (int i = 0; i < 6; i++) {
            testPosition(x,y,2*signa[i][0],2*signa[i][1],v,positions);
        }
        return positions;

    }

    public int getMaxPlayers() {
        return 6;
    }

    //TODO: make these getJumpPositions WAAAAAAAY more efficient

}