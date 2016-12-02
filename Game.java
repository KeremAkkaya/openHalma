import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.io.*;

public class Game implements Serializable
{
    private StarBoard board;
    private LinkedList<Move> moves = new LinkedList<>();
    private LinkedList<Move> cachedMoves = new LinkedList<>(); //additional list for 'undo'/'redo' functionality
    private LinkedList<Player> players = new LinkedList<>();
    private Interface iface;
    private Position hoverPosition = new Position(-1,-1);
    private Position selectedPosition = new Position(-1,-1);
    private LinkedList<Position> possibleJumps = new LinkedList<>();
    private LinkedList<Player> winners = new LinkedList<>();

    public Game() {

    }

    public Game(StarBoard board, LinkedList<Player> players) {
        this.board = board;
        this.players = players;
        this.iface = new Graphical(this);
    }

    public boolean start() {
        Logger.log(LOGGER_LEVEL.GAMEINFO, "Game started");
        if (players.size() < 2) {
            //why not one player mode make as few moves as possible...?
            Logger.log(LOGGER_LEVEL.GAMEINFO, "Only one player, exiting...");
            return false;
        }
        while (!isFinished()) {
            if (winners.contains(getNextPlayer())) players.addLast(players.removeFirst()); //skip if player is finished
            requestMove();
        }
        Logger.log(LOGGER_LEVEL.GAMEINFO, "Game finished");
        return true;
    }

    public void requestMove() {
        Move move;
        Player p = getNextPlayer();
        do {
            move = p.requestMove(board, new LinkedList<>(players), p); //wait til valid turn
        } while ((!board.isValidMove(move)) && (getNextPlayer() == p));
        if (p == getNextPlayer()) {
            tryMove(move);
        }
    }

    public boolean tryMove(Move move) {
        Player p = getNextPlayer();
        if (board.getJumpPositions(move.start, p).contains(move.end)) {
            makeMove(move);
            if (p.isFinished(board)) {
                winners.push(p);
                Logger.log(LOGGER_LEVEL.GAMEINFO, p.toString() + " finished the game as " + winners.size() + ".");
            }
            return true;
        }
        return false;
    }

    private void makeMove(Move move) {
        Logger.log(LOGGER_LEVEL.GAMEINFO, move.toString());
        applyMove(move);
        moves.add(move);
        cachedMoves.clear();
        players.addLast(players.removeFirst());
    }

    private void applyMove(Move move) {
        board.setPosition(move.start, Player.emptyPlayer.getFieldValue());
        board.setPosition(move.end, move.player.getFieldValue());
        iface.repaint();
    }

    public boolean undoMove() {
        if (undoable()) {
            Move m = moves.getLast();
            applyMove(new Move(m.player, m.end, m.start));
            players.addFirst(players.removeLast());
            cachedMoves.addLast(moves.removeLast());
            return true;
        } else {
            return false;
        }
    }

    public boolean redoMove() {
        if (redoable()) {
            applyMove(cachedMoves.getLast());
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
                    if (tryMove(new Move(getNextPlayer(), selectedPosition, hoverPosition))) {
                        selectedPosition = Position.InvalidPosition;
                    } else if ((board.getPosition(hoverPosition).getVal() >= 0) && (getNextPlayer().equals(board.getPosition(hoverPosition).getPlayer()))) selectedPosition = hoverPosition;
                }
            }
        } else {
            selectedPosition = Position.InvalidPosition;
        }
        Logger.log(LOGGER_LEVEL.DEBUG, "Clicked " + hoverPosition.toString());
        hoverPosition(hoverPosition);
    }

    public StarBoard getBoard() {
        return board;
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public LinkedList<Position> getPossibleJumps() {
        return possibleJumps;
    }
}
