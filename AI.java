import java.util.*;
public class AI
{
    private static double reference = (double) 1000;

    public enum STRATEGY {
        RANDOM, MINIMAX, FARTHEST
    }
    //TODO: make ai threaded

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
        double val, maxval = -Double.MAX_VALUE;
        for (Position start : board.getPositionByPlayer(aPlayer)) {
            for (Position target: board.getJumpPositions(start, aPlayer)) {

                move = new Move(aPlayer, start, target);
                simBoard = board.simulateMove(move);
                val = e.evaluateBoard(simBoard, aPlayer, aPlayer);
                Logger.log(LOGGER_LEVEL.AI_DEBUG, start.toString() + " -> " + target.toString() + " is worth " + val + " for " + aPlayer.toString());

                if (val > maxval) {
                    maxval = val;
                    bestmove = move;
                }
            }
        }
        return bestmove;
    }

    public static Pair<Move, Double> minimax(StarBoard board, LinkedList<Player> players, int depth, Player aPlayer, Evaluator e) {
        if (depth == 0) {
            return new Pair(Move.nullMove, e.evaluateBoard(board, players.getFirst(), aPlayer));
        }
        Player currentPlayer = players.removeFirst();
        players.addLast(currentPlayer);
        StarBoard simBoard, minBoard = null, maxBoard = null;
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        double val;
        Move move, minMove = null, maxMove = null;
        for (Position start : board.getPositionByPlayer(currentPlayer)) {
            for (Position target : board.getJumpPositions(start, currentPlayer)) {
                move = new Move(currentPlayer, start, target);
                simBoard = board.simulateMove(move);
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
            Position tip = p.getTip();
            boolean win = true;
            for (Position pos : b.getAllPositions()) {
                if (b.getPosition(pos) == aPlayer.fieldValue) {
                    if (!p.getTargetPositions().contains(pos)) {
                        win = false;
                        sum += b.pointDistance(tip, pos);
                    }
                }
            }
            if (win) return p == aPlayer ? reference : -reference;
            return p == aPlayer ? -sum : sum;
        }

    }

}