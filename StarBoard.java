import java.util.*;

public class StarBoard
{
    protected FIELD_VALUE board[][];

    protected int dimension; //size of matrix representing the board

    public final static int[][] signa = new int[][]{ //these are the signs for that indicate the 6 directions in the 2d matrix (the board)
            { 1,-1 },
            {-1, 1 },
            { 0, 1 },
            { 1, 0 },
            { 0,-1 },
            {-1, 0 }
        };
        
    public final static int directions = 6;
    public final static int maxPlayers = 6;

    public StarBoard() {
        //Logger.log(LOGGER_LEVEL.TEMP_DEBUG, "Starboard instantiated");
    }

    public StarBoard(int dimension) {
        this();
        this.dimension = dimension;
        board = new FIELD_VALUE[dimension][dimension];
    }

    public StarBoard(FIELD_VALUE[][] board, int dimension) //constructor used to load a saved game
    {
        this();
        this.dimension = dimension;
        this.board = board;
    }

    public FIELD_VALUE getPosition(Position p) {
        return getPosition(p.x, p.y);
    }

    public void applyMoveUnchecked(Move m) {
        setPosition(m.start, Player.emptyPlayer.getFieldValue());
        setPosition(m.end, m.player.getFieldValue());
    }

    public StarBoard simulateMove(Move move) {
        FIELD_VALUE nBoard[][] = new FIELD_VALUE[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            System.arraycopy(board[i], 0, nBoard[i], 0, dimension);
        }
        StarBoard b = new StarBoard(nBoard, this.dimension);
        b.applyMoveUnchecked(move);
        return b;
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
                s += board[j][i].getSymbol() + " "; //switch i and j to have coherent representation in text and graphical mode
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
            return getPosition(ax + sigx, ay + sigy).getVal() >= 0;
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

    public double pointDistance(Position a, Position b) {
        double gax = getCoordinateX(a.x, a.y);
        double gay = getCoordinateY(a.y);
        double gbx = getCoordinateX(b.x, b.y);
        double gby = getCoordinateY(b.y);

        return Math.sqrt(Math.pow(gax - gbx, 2) + Math.pow(gay - gby, 2));
    }

    private static final int CIRCLE_RADIUS = 15;
    private static final double CIRCLE_DISTANCE = 2.5 * CIRCLE_RADIUS;//25; //distance center to center, should be > 2*CIRCLE_RADIUS
    private static final double PADDING_VERTICAL = CIRCLE_DISTANCE * Math.sqrt(3.0 / 4.0);
    private static final double PADDING_HORIZONTAL = CIRCLE_DISTANCE / 2;

    private double getCoordinateX(int x, int y) {
        return ((y * PADDING_HORIZONTAL) + (x * CIRCLE_DISTANCE) - (((getDimension() - 1) / 3) * CIRCLE_DISTANCE + 2 * CIRCLE_RADIUS));
    }

    private double getCoordinateY(int y) {
        return ((y * PADDING_VERTICAL));
    }


    public FIELD_VALUE getPosition(int x, int y) {
        if ((x < dimension) && (y < dimension) && (x >= 0) && (y >= 0)) {
            return board[x][y];
        }
        return FIELD_VALUE.INVALID;
    }

    public void setPosition(Position p, FIELD_VALUE val) {
        if ((p.x >= dimension) || (p.y >= dimension) || (p.x < 0) || (p.y < 0)) {
            Logger.log(LOGGER_LEVEL.WARNING, "Trying to set position out of bounds");
            return;
        }
        board[p.x][p.y] = val;
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


    public boolean isValidMove(Move move) {
        if (move == null) System.out.println("sproing whole move");
        if (move.start == null) System.out.println("sproing start");
        if (move.end == null) System.out.println("sproing end");
        if (move.player == null) System.out.println("sproing player");
        return getJumpPositions(move.start, move.player).contains(move.end);
    }

    public String writeToString() { //save current board in a string
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

    public boolean readFromString(String s) { //restore board from string
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

    public boolean equals(Object b) {
        if (!(b instanceof StarBoard)) return false;
        if (((StarBoard) b).dimension != this.dimension) return false;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.board[i][j] != ((StarBoard) b).board[i][j]) return false;
            }
        }
        return true;
    }

    public LinkedList<Position> getJumpPositions(Position pos) {
        return getJumpPositions(pos, null);
    }

    public List<Position> getAllPositions() {
        LinkedList<Position> allPos = new LinkedList<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                allPos.add(new Position(i, j));
            }
        }
        return allPos;
    }

    public List<Position> getPositionByPlayer(Player p) {
        LinkedList<Position> playerPos = new LinkedList<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (getPosition(i, j).equals(p.getFieldValue())) {
                    playerPos.add(new Position(i, j));
                }
            }
        }
        return playerPos;
    }

    //TODO: make these getJumpPositions WAAAAAAAY more efficient

}