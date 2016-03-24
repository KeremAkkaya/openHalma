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

    private BoardFactory()
    {

    }

    private static BoardFactory getInstance() {
        if (instanceBoardFactory == null) {
            instanceBoardFactory = new BoardFactory();
        }
        return instanceBoardFactory;
    }

    private StarBoard _createStarBoard(int dimension, LinkedList<Player> players, boolean b_reduce) {
        if((players.size() > 3) && !b_reduce) {
            System.out.println("standard starboard only up to 3 players, use reduce instead");
            return null;
        }

        int i_reduce = b_reduce ? 1 : 0;
        StarBoard sb = new StarBoard(dimension);
        StarBoard.StandardStar_Values vals = sb.getValues();
        sb.initBoard();
        final int tempred=0;
        st_makeHexagon(FIELD_VALUE.EMPTY, sb, vals);
        st_makeTriangle1(Player.emptyPlayer.getFieldValue(),sb,tempred,vals);
        st_makeTriangle2(Player.emptyPlayer.getFieldValue(),sb,tempred,vals);
        st_makeTriangle3(Player.emptyPlayer.getFieldValue(),sb,tempred,vals);
        st_makeTriangle4(Player.emptyPlayer.getFieldValue(),sb,tempred,vals);
        st_makeTriangle5(Player.emptyPlayer.getFieldValue(),sb,tempred,vals);
        st_makeTriangle6(Player.emptyPlayer.getFieldValue(),sb,tempred,vals);
        
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

    public static StarBoard createStandardStarBoard(int dimension, LinkedList<Player> players, boolean b_reduce) {
        return getInstance()._createStarBoard(dimension, players, b_reduce);
    }

    private void st_makeHexagon(FIELD_VALUE fv, StarBoard sb, StarBoard.StandardStar_Values vals) {
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

    private void st_makeTriangle1(FIELD_VALUE fv, StarBoard sb, int i_reduce, StarBoard.StandardStar_Values vals) {
        for (int i = 0; i <= (vals.short_triangle - i_reduce); i++) {
            for (int j = vals.dimension - 1; j >= (vals.dimension - 1 - i); j--) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle2(FIELD_VALUE fv, StarBoard sb, int i_reduce, StarBoard.StandardStar_Values vals) {
        for (int i = vals.long_triangle - 1; i <= vals.edge_hexagon - 1; i++) {
            for (int j = vals.short_triangle; j < (vals.edge_hexagon - i_reduce - (i-vals.short_triangle)); j++) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle3(FIELD_VALUE fv, StarBoard sb, int i_reduce, StarBoard.StandardStar_Values vals) {
        for (int i = vals.edge_hexagon - 1; i <= vals.full - vals.long_triangle; i++) {
            for (int j = vals.short_triangle - i_reduce; j >= vals.short_triangle - (i - (vals.edge_hexagon - 1)); j--) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle4(FIELD_VALUE fv, StarBoard sb, int i_reduce, StarBoard.StandardStar_Values vals) {
        for (int i = vals.dimension + i_reduce - 1; i < vals.full; i++) {
            for(int j = vals.short_triangle; j < vals.edge_hexagon - 1 - (i - vals.dimension); j++) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle5(FIELD_VALUE fv, StarBoard sb, int i_reduce, StarBoard.StandardStar_Values vals) {
        for (int i = vals.edge_hexagon - 1; i <= vals.full - vals.long_triangle; i++) {
            for (int j = vals.dimension - 1; j >= vals.dimension - 1 - (i - vals.edge_hexagon + 1 - i_reduce); j--) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }

    private void st_makeTriangle6(FIELD_VALUE fv, StarBoard sb, int i_reduce, StarBoard.StandardStar_Values vals) {
        for (int i = vals.short_triangle; i < vals.edge_hexagon; i++) {
            for (int j = vals.dimension - 1 + i_reduce; j < vals.full - (i - vals.short_triangle); j++) {
                sb.setPosition(new Position(i, j), fv);
            }
        }
    }
}