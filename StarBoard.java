import java.util.*;
/**
 * Write a description of class StarBoard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StarBoard extends Board
{
    
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
        _getJumpPositions(pos,p,positions);
        //add 1 jump moves
        return positions;
    }

    private boolean inHexagon(Position a, Position b, int radius) {
        int diffx = b.x - a.x, diffy = b.y - a.y;
        if((diffx == -radius) && (diffy == radius)) {
            return true;
        }
        if((diffx == radius) && (diffy == 0)) {
            return true;
        }
        if((diffx == 0) && (diffy == radius)) {
            return true;
        }
        if((diffx == radius) && (diffy == -radius)) {
            return true;
        }
        if((diffx == 0) && (diffy == -radius)) {
            return true;
        }
        if((diffx == -radius) && (diffy == 0)) {
            return true;
        }
        return false;
    }

    private boolean isValidJump(Position a, Position b, Player p) {
        int sigx = (int)Math.signum(b.x - a.x), sigy = (int)Math.signum(b.y - a.y);
        if ( ( (getPosition(a) != p.getFieldValue()) && (getPosition(a) != FIELD_VALUE.EMPTY) ) || (getPosition(b) != FIELD_VALUE.EMPTY) ) {
            return false;
        }
        if (inHexagon(a,b,1)) {
            return true;
        }
        if (inHexagon(a,b,2)) {
            if (getPosition(new Position(a.x + sigx, a.y + sigy)).getVal() >= 0) return true;
            return false;
        }
        return false;
    }

    private boolean testPosition(Position pos, int offsetx, int offsety, Player p, LinkedList<Position> positions) {
        Position target = new Position(pos.x + offsetx, pos.y + offsety);
        if (positions.contains(target)) return false;
        if (isValidJump(pos, new Position(pos.x + offsetx, pos.y + offsety), p)) {
            positions.add(target);
            _getJumpPositions(target, p, positions);
            return true;
        }
        return false;
    }

    private LinkedList<Position> _getJumpPositions(Position pos, Player p, LinkedList<Position> positions) {
        //Jumps in 6 directions
        testPosition(pos,-2,2,p,positions);
        testPosition(pos,2,-2,p,positions);
        testPosition(pos,0,2,p,positions);
        testPosition(pos,2,0,p,positions);
        testPosition(pos,-2,0,p,positions);
        testPosition(pos,0,-2,p,positions);

        return positions;

    }
    //TODO: make these getJumpPositions WAAAAAAAY more efficient
    /*public boolean allowLongJumps() {
    return params.allowLongJumps;
    }*/
    //add load from file and save to file serializable?

}