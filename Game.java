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

    public Game() {

    }

    public Game(StarBoard board) {
        //this.iface.setGame(this);
        this.board = board;
        this.iface = new Graphical(this);
    }

    public boolean addPlayer(Player p) {
        if (players.size() < board.getMaxPlayers()) {
            if (!players.contains(p)) {
                players.addLast(p);
                return true;
            }
        }
        return false;
    }

    public boolean removePlayer(Player p) {
        if (players.contains(p)) {
            players.remove(p);
            return true;
        }
        return false;
    }

    public boolean start() {
        System.out.println("game started");
        if (players.size() < 2) {
            return false;
        }
        while (!isFinished()) requestMove();
        System.out.println("game finished");
        return true;
    }

    public boolean tryMove(Move move) {
        if(board.getJumpPositions(move.start, getNextPlayer()).contains(move.end)) {
            makeMove(move);
            return true;
        }
        return false;
    }

    private void makeMove(Move move) {
        //TODO: change the currentPosition in Player
        //TODO: check whether player finished the game
        System.out.println(move.toString());
        applyMove(move);
        moves.add(move);
        cachedMoves.clear();
        players.addLast(players.removeFirst());
    }

    private void applyMove(Move move) {
        move.player.moveToken(move);
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
        boolean finished = true;
        for (Player p : players) {
            finished &= p.isFinished();
        }
        return finished;
    }

    public void requestMove() {
        Move move;
        Player p = getNextPlayer();
        do {
            move = p.requestMove(board, new LinkedList<Player>(players), p); //wait til valid turn
        } while ((!board.isValidMove(move)) && (getNextPlayer() == p));
        if (p == getNextPlayer()) {
            tryMove(move);
        }
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
        System.out.println(hoverPosition);
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
