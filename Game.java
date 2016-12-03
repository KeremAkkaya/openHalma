import java.util.LinkedList;
import java.io.*;

public class Game implements Serializable
{
    public final StarBoard board;
    private LinkedList<Move> moves = new LinkedList<>();
    private LinkedList<Move> cachedMoves = new LinkedList<>(); //additional list for 'undo'/'redo' functionality
    private LinkedList<Player> players = new LinkedList<>();
    private final Interface iface;
    private Position hoverPosition = new Position(-1,-1);
    private Position selectedPosition = new Position(-1,-1);
    private LinkedList<Position> possibleJumps = new LinkedList<>();
    private LinkedList<Player> winners = new LinkedList<>();


    public Game(StarBoard board, LinkedList<Player> players) {
        this.board = board;
        this.players = players;
        this.iface = new Graphical(this);
    }

    public void start() {
        Logger.log(LOGGER_LEVEL.GAMEINFO, "Game started");
        if (players.size() < 2 && players.get(0) instanceof LocalPlayer) {
            //why not one player mode make as few moves as possible...?
            Logger.log(LOGGER_LEVEL.GAMEINFO, "Only one player, have fun...");
            Logger.log(LOGGER_LEVEL.GAMEINFO, "Do you know why there is an AI implemented in this software?");
        }
        Logger.log(LOGGER_LEVEL.TEMP_DEBUG, "got this far");
        if (getNextPlayer() instanceof ComputerPlayer) {
            requestMove();
        }
    }

    public void finished() {
        Logger.log(LOGGER_LEVEL.GAMEINFO, "Game finished");
    }

    public void requestMove() {
        Move move;
        Player p = getNextPlayer();

        do {
            Logger.log(LOGGER_LEVEL.TEMP_DEBUG, "requesting from " + p.toString());
            move = p.requestMove(board, new LinkedList<>(players), p); //wait til valid turn
            //System.out.println("busy waiting");
        } while ((!board.isValidMove(move)) && (getNextPlayer() == p));
        tryMove(move);
    }

    public void tryMove(Move move) {
        Player p = getNextPlayer();
        if (board.isValidMove(move)) {
            makeMove(move);
            if (p.isFinished(board)) {
                winners.push(p);
                players.remove(p);
                Logger.log(LOGGER_LEVEL.GAMEINFO, p.toString() + " finished the game as " + winners.size() + ".");
                if (isFinished()) {
                    finished();
                    return;
                }
            }
            Logger.log(LOGGER_LEVEL.GAMEINFO, "Next player: " + getNextPlayer().toString());
            if (getNextPlayer() instanceof ComputerPlayer) {
                requestMove();
            } else {

            }
        }
    }

    private void makeMove(Move move) {
        Logger.log(LOGGER_LEVEL.GAMEINFO, move.toString());
        board.applyMoveUnchecked(move);
        iface.repaint();
        moves.add(move);
        cachedMoves.clear();
        players.addLast(players.removeFirst());
    }


    public boolean undoMove() {
        if (undoable()) {
            Move m = moves.getLast();
            board.applyMoveUnchecked(new Move(m.player, m.end, m.start));
            iface.repaint();
            players.addFirst(players.removeLast());
            cachedMoves.addLast(moves.removeLast());
            return true;
        } else {
            return false;
        }
    }

    public boolean redoMove() {
        if (redoable()) {
            board.applyMoveUnchecked(cachedMoves.getLast());
            iface.repaint();
            players.addLast(players.removeFirst());
            moves.addLast(cachedMoves.removeLast());
            return true;
        } else {
            return false;
        }
    }

    public boolean undoable() {
        return moves.peekFirst() != null;
    }

    public boolean redoable() {
        return cachedMoves.peekFirst() != null;
    }

    public Player getNextPlayer() {
        return players.peekFirst();
    }

    public boolean isFinished() {
        for (Player p : players) {
            if (!(winners.contains(p))) return false;
        }
        return true;
    }

    private boolean validHover() {
        return (!hoverPosition.equals(Position.InvalidPosition));
    }

    public void hoverPosition(Position pos) {
        hoverPosition = pos;
        boolean selected = !selectedPosition.equals(Position.InvalidPosition);
        if (!selected && (validHover())) {
            possibleJumps = board.getJumpPositions(hoverPosition);
        } else if (selected) {
            possibleJumps = board.getJumpPositions(selectedPosition);
        } else {
            selectedPosition = Position.InvalidPosition;
            possibleJumps.clear();
        }
        iface.repaint();
    }

    public void click() {
        if (getNextPlayer() instanceof LocalPlayer) {
            if (validHover()) {
                if (hoverPosition.equals(selectedPosition)) {
                    selectedPosition = Position.InvalidPosition;
                } else {
                    Move move = new Move(getNextPlayer(), selectedPosition, hoverPosition);
                    if (board.isValidMove(move)) {
                        tryMove(move);
                        selectedPosition = Position.InvalidPosition;
                    } else if ((board.getPosition(hoverPosition).getVal() >= 0) &&
                            (getNextPlayer().equals(board.getPosition(hoverPosition).getPlayer()))) {
                        selectedPosition = hoverPosition;
                    }
                }
            }
        } else {
            selectedPosition = Position.InvalidPosition;
        }
        Logger.log(LOGGER_LEVEL.DEBUG, "Clicked " + hoverPosition.toString());
        hoverPosition(hoverPosition);
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public LinkedList<Position> getPossibleJumps() {
        return possibleJumps;
    }
}
