
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
            this.dimension = dimension;
            short_triangle = (dimension - 1) /  3;
            long_triangle = short_triangle + 1;
            full = dimension + short_triangle;
            edge_hexagon = dimension - short_triangle;
        } 
    }

    StandardStar_Values vals;
    
    public StarBoard(int dimension) {
        if (dimension % 3 != 1) {
            System.out.println("create starboard wrong dimension!");
            return;
        }
        vals = new StandardStar_Values(dimension);
        board = new FIELD_VALUE[vals.full][vals.full];
    }

    public StarBoard(FIELD_VALUE[][] board, int dimension)
    {
        this(dimension);
        //board is created empty and then replaced by another one :/ its ok for the moment
        this.board = board;
    }
    
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

    //add load from file and save to file
}
