import java.util.*;
public class AI
{
    private static double reference = 15000;

    public enum STRATEGY {
        RANDOM, MINIMAX, FARTHEST
    }

    public static class Pair<Board, Integer> {
        public final Move move;
        public final Integer value;

        public Pair (Move m, Integer i) {
            this.move = m;
            this.value = i;
        }
    }

    public static Move move(StarBoard board, int depth, LinkedList<Player> players, STRATEGY strategy, Player aPlayer) {
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

    public static Move farthest(StarBoard board, Player aPlayer, Evaluator e) {
        StarBoard simBoard;
        Move move, bestmove = Move.nullMove;
        double val, maxval = Double.MIN_VALUE;
        for (Position start: aPlayer.getCurrentPositions()) {
            for (Position target: board.getJumpPositions(start, aPlayer)) {
                //System.out.println("analyzing from " + start.toString() + " to " + target.toString());

                move = new Move(aPlayer, start, target);
                simBoard = board.simulateMove(move);
                val = e.evaluateBoard(simBoard, aPlayer, aPlayer);
                //System.out.println(val);
                //System.out.println(e.evaluateBoard(board, aPlayer, aPlayer));
                if (val > maxval) {
                    maxval = val;
                    bestmove = move;
                }
            }
        }
        return bestmove;
    }

    public static Pair<Move, Integer> minimax(StarBoard b, LinkedList<Player> players, int depth, Player aPlayer, Evaluator e) {
        if (depth == 0) {
            return new Pair(Move.nullMove, e.evaluateBoard(b, players.getFirst(), aPlayer));
        }
        Player currentPlayer = players.removeFirst();
        players.addLast(currentPlayer);
        StarBoard simBoard, minBoard = null, maxBoard = null;
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

    public static abstract class Evaluator {
        public abstract double evaluateBoard(StarBoard b, Player p, Player aPlayer);
    }

    public static class SimpleDistanceEvaluator extends Evaluator {

        public double evaluateBoard(StarBoard b, Player p, Player aPlayer) {
            double sum = 0;
            for (Position pos : b.getAllPositions()) {
                if (b.getPosition(pos) == aPlayer.fieldValue) {
                    if (!p.getTargetPositions().contains(pos)) {
                        sum += b.pointDistance(p.getTip(), pos);
                    }
                }
            }
            return p == aPlayer ? reference - sum : - reference + sum;
        }

    }

}