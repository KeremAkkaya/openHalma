import java.util.*;
/**
 * Write a description of class StarBoard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StarBoard extends Board
{

    public class StandardStar_Values { //contains length of edges of the star board dependent on the dimension
        public final int dimension;         //=7
        public final int short_triangle;    //=2
        public final int long_triangle;     //=3
        public final int full;              //=9
        public final int edge_hexagon;      //=5

        public StandardStar_Values(int dimension) {
            if (dimension % 3 != 1) {
                System.out.println("create starboard wrong dimension! expect crash now");
            }
            this.dimension = dimension;
            short_triangle = (dimension - 1) /  3;
            long_triangle = short_triangle + 1;
            full = dimension + short_triangle;
            edge_hexagon = dimension - short_triangle;
        }
    }

    public class StandardStar_Parameters {
        public final boolean allowLongJumps;

        public StandardStar_Parameters(boolean longJumps) {
            allowLongJumps = longJumps;
        }

        public StandardStar_Parameters() {
            this(false);
        }
    }

    StandardStar_Values vals;
    StandardStar_Parameters params;

    public StarBoard(int dimension) {
        this(dimension, false);
    }

    public StarBoard(int dimension, boolean longJumps) {
        vals = new StandardStar_Values(dimension);
        params = new StandardStar_Parameters(longJumps);
        board = new FIELD_VALUE[vals.full][vals.full];
    }

    public StarBoard(FIELD_VALUE[][] board, int dimension, StandardStar_Parameters params) //constructor used to load a saved game
    {
        this(dimension);
        this.params = params;
        //board is created empty and then replaced by another one :/ its ok for the moment
        this.board = board;
    }

    //public

    public void initBoard() {
        for (int i = 0; i < vals.full; i++) {
            for (int j = 0; j < vals.full; j++) {
                board[i][j] = FIELD_VALUE.INVALID;
            }
        }
    }

    public StandardStar_Values getValues() {
        return vals;
    }

    public StandardStar_Parameters getParams() {
        return params;
    }

    public String toText() {
        String s = "";
        for (int i = 0; i < vals.full; i ++) {
            for (int k = 0; k < i; k++) s += " ";
            for (int j = 0; j < vals.full; j++) {
                s += board[i][j].getSymbol() + " ";
            }
            s += "\n";
        }
        return s;
    }

    public int getDimension() {
        return vals.full;
    }

    public LinkedList<Position> getJumpPositions(Position pos, Player p) {
        LinkedList<Position> positions = new LinkedList<>();
        if(!(getPosition(pos).equals(p.getFieldValue()))) {
            //try to move other than player
            return positions;
        }
        //add 2 jump moves
        _getJumpPositions(pos,p,positions);
        //add 1 jump moves
        return positions;
    }

    private boolean inHexagon(Position a, Position b, int radius) {
        int diffx = b.x - a.x, diffy = b.y - a.y;
        if((diffx == radius) && (diffy == radius)) {
            return true;
        }
        if((diffx == radius) && (diffy == 0)) {
            return true;
        }
        if((diffx == 0) && (diffy == radius)) {
            return true;
        }
        if((diffx == -radius) && (diffy == -radius)) {
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
        testPosition(pos,2,2,p,positions);
        testPosition(pos,-2,-2,p,positions);
        testPosition(pos,0,2,p,positions);
        testPosition(pos,2,0,p,positions);
        testPosition(pos,-2,0,p,positions);
        testPosition(pos,0,-2,p,positions);
        
        return positions;

    }
    /*public boolean allowLongJumps() {
    return params.allowLongJumps;
    }*/
    //add load from file and save to file serializable?

}