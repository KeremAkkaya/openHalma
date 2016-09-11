import java.util.*;
public class AI
{
    private static double reference = 15000;
    public static enum STRATEGY {
        RANDOM, MINIMAX, FARTHEST;
    }

    public static class Pair<Board, Integer> {
        public final Move move;
        public final Integer value;

        public Pair (Move m, Integer i) {
            this.move = m;
            this.value = i;
        }
    }

    public static Move move(Board board, int depth, LinkedList<Player> players, STRATEGY strategy, Player aPlayer) {
        System.out.println("move");
        switch (strategy) {
            case RANDOM:
            break;
            case MINIMAX:
            return minimax(board, players, 3, aPlayer, new SimpleDistanceEvaluator()).move;
            case FARTHEST:
            return farthest(board, aPlayer, new SimpleDistanceEvaluator());
        }
        return Move.nullMove;
    }

    public static Move farthest(Board board, Player aPlayer, Evaluator e) {
        System.out.println("yoyo");
        Board simBoard;
        Move move, bestmove = Move.nullMove;
        double val, maxval = Double.MIN_VALUE;
        for (Position start: aPlayer.getCurrentPositions()) {
            for (Position target: board.getJumpPositions(start, aPlayer)) {
                move = new Move(aPlayer, start, target);
                simBoard = board.simulateMove(move);
                val = e.evaluateBoard(simBoard, aPlayer, aPlayer);
                if (val > maxval) {
                    System.out.println(val);
                    maxval = val;
                    bestmove = move;
                }
            }
        }
        return bestmove;
    }

    public static Pair<Move, Integer> minimax(Board b, LinkedList<Player> players, int depth, Player aPlayer, Evaluator e) {
        //System.out.println("nono");
        if (depth == 0) {
            return new Pair(Move.nullMove, e.evaluateBoard(b, players.getFirst(), aPlayer));
        }
        Player currentPlayer = players.removeFirst();
        players.addLast(currentPlayer);
        Board simBoard, minBoard = null, maxBoard = null;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int val;
        Move move, minMove = null, maxMove = null;
        for (Position start: currentPlayer.getCurrentPositions()) {
            for (Position target: b.getJumpPositions(start, currentPlayer)) {
                move = new Move(currentPlayer, start, target);
                simBoard = b.simulateMove(move);
                val = minimax(simBoard, players, depth - 1, aPlayer, e).value;
                if (val < min) {
                    min = val;
                    minBoard = simBoard;
                    minMove = move;
                }
                if (val > max) {
                    max = val;
                    maxBoard = simBoard;
                    maxMove = move;
                }
            }
        }

        if (aPlayer == currentPlayer) {
            return new Pair(maxMove, max);
        } else {
            return new Pair(minMove, min);
        }

    }

    public static double pointDistance(Position a, Position b, Board board) {
        int diffx, diffy;
        diffx = b.x - a.x;
        diffy = b.y - a.y;
        int sum = 0;

        while ((diffx != 0) || (diffy != 0)) {
            sum++;
            for (int i = 0; i < board.getNumDirections(); ++i) {
                if ((Math.signum(diffx) == board.getSigna()[i][0]) && (Math.signum(diffy) == board.getSigna()[i][1])) {
                    diffx -= board.getSigna()[i][0];
                    diffy -= board.getSigna()[i][1];
                    break;
                }
            }
        }
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));

    }

    public static abstract class Evaluator {
        public abstract double evaluateBoard(Board b, Player p, Player aPlayer);
    }

    public static class SimpleDistanceEvaluator extends Evaluator {

        public double evaluateBoard(Board b, Player p, Player aPlayer) {
            double sum = 0;
            for (Position pos : p.getCurrentPositions()) {
                if (! p.getTargetPositions().contains(pos)) {
                    sum += pointDistance(p.getTip(), pos, b);
                }
            }
            return p == aPlayer ? reference - sum : - reference + sum;
        }

    }

}