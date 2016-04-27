import java.util.LinkedList;
import java.util.List;

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
        public final int dimension;         //=7 // 13 // 10
        public final int short_triangle;    //=2 // 4  // 3
        public final int long_triangle;     //=3 // 5  // 4
        public final int full;              //=9 // 17 // 13   this is the 'real' dimension of the board, namely the width and height of the matrix
        public final int edge_hexagon;      //=5 // 9  // 7

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
    //TODO: instead of directly interacting with the board, set the currentPositions and targetPosition + direction for each player and let (game/player/board) init them

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
        LinkedList<Position> pos = new LinkedList<>();
        pos.addAll(st_makeHexagon(vals));
        pos.addAll(st_makeTriangle1(0,vals));
        pos.addAll(st_makeTriangle2(0,vals));
        pos.addAll(st_makeTriangle3(0,vals));
        pos.addAll(st_makeTriangle4(0,vals));
        pos.addAll(st_makeTriangle5(0,vals));
        pos.addAll(st_makeTriangle6(0,vals));

        for(Position posi : pos) {
            sb.setPosition(posi,FIELD_VALUE.EMPTY);
        }
        switch(players.size()) {
            case 5:
                players.get(4).setCurrentPositions(st_makeTriangle4(i_reduce,vals));
                players.get(3).setCurrentPositions(st_makeTriangle2(i_reduce,vals));

            case 3:
                players.get(1).setCurrentPositions(st_makeTriangle3(i_reduce,vals));
                players.get(2).setCurrentPositions(st_makeTriangle5(i_reduce,vals));

            case 1:
                players.get(0).setCurrentPositions(st_makeTriangle1(i_reduce,vals));
                break;


            case 6:
                players.get(4).setCurrentPositions(st_makeTriangle1(i_reduce,vals));
                players.get(5).setCurrentPositions(st_makeTriangle4(i_reduce,vals));

            case 4:
                players.get(2).setCurrentPositions(st_makeTriangle2(i_reduce,vals));
                players.get(3).setCurrentPositions(st_makeTriangle5(i_reduce,vals));

            case 2:
                players.get(0).setCurrentPositions(st_makeTriangle3(i_reduce,vals));
                players.get(1).setCurrentPositions(st_makeTriangle6(i_reduce,vals));

            case 0:
                break;

            default:
            System.out.println("number of players not supported");
            return null;
        }

        for (Player p : players) {
            p.initPositions(sb);
        }
        return sb;
    }
    
    //dimension size of the matrix
    //players list of players, theoretically the size of players would be sufficient
    //i_reduce: number of lines towards the center of the board that are NOT filled with tokens (usually set to 0)
    public static StarBoard createStandardStarBoard(int dimension, LinkedList<Player> players, int i_reduce) {
        return getInstance()._createStarBoard(dimension, players, i_reduce);
    }

    private LinkedList<Position> st_makeHexagon(StandardStar_Values vals) {
        LinkedList<Position> pos = new LinkedList<Position>();
        for (int i = vals.short_triangle; i < vals.edge_hexagon - 1; i++) {
            for (int j = vals.edge_hexagon - 1 - (i - vals.short_triangle); j < vals.dimension; j++) {
                pos.add(new Position(i,j));
            }
        }
        for (int i = vals.edge_hexagon - 1; i < vals.dimension; i++) {
            for (int j = vals.long_triangle - 1; j < vals.dimension - 1 - (i - vals.edge_hexagon); j++) {
                pos.add(new Position(i,j));
            }
        }
        return pos;
    }

    private LinkedList<Position> st_makeTriangle1(int i_reduce, StandardStar_Values vals) {
        LinkedList<Position> pos = new LinkedList<Position>();
        for (int i = 0; i <= (vals.short_triangle - i_reduce); i++) {
            for (int j = vals.dimension - 1; j >= (vals.dimension - 1 - i); j--) {
                pos.add(new Position(i, j));
            }
        }
        return pos;
    }

    private LinkedList<Position> st_makeTriangle2(int i_reduce, StandardStar_Values vals) {
        LinkedList<Position> pos = new LinkedList<Position>();
        for (int i = vals.long_triangle - 1; i <= vals.edge_hexagon - 1; i++) {
            for (int j = vals.short_triangle; j < (vals.edge_hexagon - i_reduce - (i-vals.short_triangle)); j++) {
                pos.add(new Position(i, j));
            }
        }
        return pos;
    }

    private LinkedList<Position> st_makeTriangle3(int i_reduce, StandardStar_Values vals) {
        LinkedList<Position> pos = new LinkedList<Position>();
        for (int i = vals.edge_hexagon - 1; i <= vals.full - vals.long_triangle; i++) {
            for (int j = vals.short_triangle - i_reduce; j >= vals.short_triangle - (i - (vals.edge_hexagon - 1)); j--) {
                pos.add(new Position(i, j));
            }
        }
        return pos;
    }

    private LinkedList<Position> st_makeTriangle4(int i_reduce, StandardStar_Values vals) {
        LinkedList<Position> pos = new LinkedList<Position>();
        for (int i = vals.dimension + i_reduce - 1; i < vals.full; i++) {
            for(int j = vals.short_triangle; j < vals.edge_hexagon - 1 - (i - vals.dimension); j++) {
                pos.add(new Position(i, j));
            }
        }
        return pos;
    }

    private LinkedList<Position> st_makeTriangle5(int i_reduce, StandardStar_Values vals) {
        LinkedList<Position> pos = new LinkedList<Position>();
        for (int i = vals.edge_hexagon - 1; i <= vals.full - vals.long_triangle; i++) {
            for (int j = vals.dimension - 1; j >= vals.dimension - 1 - (i - vals.edge_hexagon + 1 - i_reduce); j--) {
                pos.add(new Position(i, j));
            }
        }
        return pos;
    }

    private LinkedList<Position> st_makeTriangle6(int i_reduce, StandardStar_Values vals) {
        LinkedList<Position> pos = new LinkedList<Position>();
        for (int i = vals.short_triangle; i < vals.edge_hexagon; i++) {
            for (int j = vals.dimension - 1 + i_reduce; j < vals.full - (i - vals.short_triangle); j++) {
                pos.add(new Position(i, j));
            }
        }
        return pos;
    }
}