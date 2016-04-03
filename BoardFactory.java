import java.util.LinkedList;
/**
 * Write a description of class BoardFactory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoardFactory
{

    private static BoardFactory instanceBoardFactory = null;
    
    
    public class StandardStar_Values { //contains length of edges of the star board dependent on the dimension
        public final int dimension;         //=7
        public final int short_triangle;    //=2
        public final int long_triangle;     //=3
        public final int full;              //=9 this is the 'real' dimension of the board, namely the width and height of the matrix
        public final int edge_hexagon;      //=5

        public StandardStar_Values(int full) {
            if (((3 * full + 1) % 4) != 0) {
                System.out.println("create starboard wrong dimension! expect crash now. must be 5 + 4x x positive");
            }
            this.full = full;
            this.dimension = (3 * full + 1) / 4;
            this.short_triangle = (dimension - 1) /  3;
            long_triangle = short_triangle + 1;
            edge_hexagon = dimension - short_triangle;
        }
    }

    private BoardFactory()
    {

    }

    private static BoardFactory getInstance() {
        if (instanceBoardFactory == null) {
            instanceBoardFactory = new BoardFactory();
        }
        return instanceBoardFactory;
    }

    private StarBoard _createStarBoard(int dimension, LinkedList<Player> players, int i_reduce) {
        if(i_reduce < 0) {
            i_reduce = 0;
            System.out.println("i_reduce must be >=0");
        }
        if((players.size() > 3) && i_reduce < 1) {
            System.out.println("standard starboard only up to 3 players, use reduce instead");
            return null;
        }

        StandardStar_Values vals = new StandardStar_Values(dimension);
        StarBoard sb = new StarBoard(vals.full);
        sb.initBoard();
        st_makeHexagon(FIELD_VALUE.EMPTY, sb, vals);
        st_makeTriangle1(Player.emptyPlayer.getFieldValue(),sb,0,vals);
        st_makeTriangle2(Player.emptyPlayer.getFieldValue(),sb,0,vals);
        st_makeTriangle3(Player.emptyPlayer.getFieldValue(),sb,0,vals);
        st_makeTriangle4(Player.emptyPlayer.getFieldValue(),sb,0,vals);
        st_makeTriangle5(Player.emptyPlayer.getFieldValue(),sb,0,vals);
        st_makeTriangle6(Player.emptyPlayer.getFieldValue(),sb,0,vals);
        
        switch(players.size()) {
            case 0:
            break;

            case 1:
            st_makeTriangle1(players.get(0).getFieldValue(),sb,i_reduce,vals);
            break;

            case 2:
            st_makeTriangle1(players.get(0).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle4(players.get(1).getFieldValue(),sb,i_reduce,vals);
            break;

            case 3:
            st_makeTriangle1(players.get(0).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle3(players.get(1).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle5(players.get(2).getFieldValue(),sb,i_reduce,vals);
            break;

            case 4:
            st_makeTriangle1(players.get(0).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle4(players.get(1).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle2(players.get(2).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle5(players.get(3).getFieldValue(),sb,i_reduce,vals);
            break;

            case 5:
            st_makeTriangle1(players.get(0).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle4(players.get(1).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle2(players.get(2).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle5(players.get(3).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle3(players.get(4).getFieldValue(),sb,i_reduce,vals);
            break;

            case 6:
            st_makeTriangle1(players.get(0).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle2(players.get(1).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle3(players.get(2).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle4(players.get(3).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle5(players.get(4).getFieldValue(),sb,i_reduce,vals);
            st_makeTriangle6(players.get(5).getFieldValue(),sb,i_reduce,vals);
            break;

            default:
            System.out.println("number of players not supported");
            return null;
        }
        return sb;
    }
    
    //dimension size of the matrix
    //players list of players, theoretically the size of players would be sufficient
    //i_reduce: number of lines towards the center of the board that are NOT filled with tokens (usually set to 0)
    public static StarBoard createStandardStarBoard(int dimension, LinkedList<Player> players, int i_reduce) {
        return getInstance()._createStarBoard(dimension, players, i_reduce);
    }

    private void st_makeHexagon(FIELD_VALUE fv, StarBoard sb, StandardStar_Values vals) {
        for (int i = vals.short_triangle; i < vals.edge_hexagon - 1; i++) {
            for (int j = vals.edge_hexagon - 1 - (i - vals.short_triangle); j < vals.dimension; j++) {
                sb.setPosition(new Position(i,j), fv);
            }
        }
        for (int i = vals.edge_hexagon - 1; i < vals.dimension; i++) {
            for (int j = vals.long_triangle - 1; j < vals.dimension - 1 - (i - vals.edge_hexagon); j++) {
                sb.setPosition(new Position(i,j), fv);
            }
        }
    }

    private void st_makeTriangle1(FIELD_VALUE fv, StarBoard sb, int i_reduce, StandardStar_Values vals) {
        for (int i = 0; i <= (vals.short_triangle - i_reduce); i++) {
            for (int j = vals.dimension - 1; j >= (vals.dimension - 1 - i); j--) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle2(FIELD_VALUE fv, StarBoard sb, int i_reduce, StandardStar_Values vals) {
        for (int i = vals.long_triangle - 1; i <= vals.edge_hexagon - 1; i++) {
            for (int j = vals.short_triangle; j < (vals.edge_hexagon - i_reduce - (i-vals.short_triangle)); j++) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle3(FIELD_VALUE fv, StarBoard sb, int i_reduce, StandardStar_Values vals) {
        for (int i = vals.edge_hexagon - 1; i <= vals.full - vals.long_triangle; i++) {
            for (int j = vals.short_triangle - i_reduce; j >= vals.short_triangle - (i - (vals.edge_hexagon - 1)); j--) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle4(FIELD_VALUE fv, StarBoard sb, int i_reduce, StandardStar_Values vals) {
        for (int i = vals.dimension + i_reduce - 1; i < vals.full; i++) {
            for(int j = vals.short_triangle; j < vals.edge_hexagon - 1 - (i - vals.dimension); j++) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle5(FIELD_VALUE fv, StarBoard sb, int i_reduce, StandardStar_Values vals) {
        for (int i = vals.edge_hexagon - 1; i <= vals.full - vals.long_triangle; i++) {
            for (int j = vals.dimension - 1; j >= vals.dimension - 1 - (i - vals.edge_hexagon + 1 - i_reduce); j--) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle6(FIELD_VALUE fv, StarBoard sb, int i_reduce, StandardStar_Values vals) {
        for (int i = vals.short_triangle; i < vals.edge_hexagon; i++) {
            for (int j = vals.dimension - 1 + i_reduce; j < vals.full - (i - vals.short_triangle); j++) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }
}