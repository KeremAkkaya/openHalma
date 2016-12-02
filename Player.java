import java.util.*;
import java.awt.Color;

public abstract class Player
{
    protected final Color p_color;
    protected final FIELD_VALUE fieldValue;
    public static Player emptyPlayer = new LocalPlayer(FIELD_VALUE.EMPTY, Color.white, "");
    protected final String name;
    protected LinkedList<Position> targetPositions;
    protected Position tip;
    protected Position direction; //this is a position because it needs to hold x and y value

    public Player() {
        this(FIELD_VALUE.EMPTY, Color.white, ""); // by default this creates the empty player
    }

    public Player(FIELD_VALUE fieldValue, Color color, String s) {
        fieldValue.setPlayer(this);
        this.fieldValue = fieldValue;
        this.p_color = (color == null) ? Color.black : color;
        this.name = (s.equals("")) ? "Player" + fieldValue.getVal() : s;
    }

    public void initPositions(StarBoard board, LinkedList<Position> posis) {
        for (Position p : posis) {
            board.setPosition(p, this.fieldValue);
        }
        double distance = 0, pdistance;
        Position center = new Position(board.dimension / 2, board.dimension / 2);
        for (Position p: targetPositions) {
            pdistance = board.pointDistance(p, center);
            if (pdistance > distance) {
                distance = pdistance;
                tip = p;
            }
        }
        Logger.log(LOGGER_LEVEL.DEBUG, this.toString() + " target (tip) is: " + tip.toString());
    }

    public void setTargetPositions(LinkedList<Position> p) {
        this.targetPositions=p;
    }

    public void setDirection(Position p) {
        this.direction = p;
    }

    public Color getColor() {
        return p_color;
    }

    public FIELD_VALUE getFieldValue() {
        return fieldValue;
    }

    public String getName() {
        return name;
    }

    //request the player to make a move
    public abstract Move requestMove(StarBoard board, LinkedList<Player> pl, Player p);

    public boolean equals (Object p) {
        return ((Player)p).fieldValue == this.fieldValue;
    }

    public Position getDirection() {
        return direction;
    }

    public Position getTip() {
        return tip;
    }

    public LinkedList<Position> getTargetPositions() {
        return targetPositions;
    }

    public boolean isFinished(StarBoard b) {
        for (Position p : targetPositions) ;// Logger.log(LOGGER_LEVEL.TEMP_DEBUG, this.toString() + " target: " + p);
        for (Position p : b.getPositionByPlayer(this)) { //TODO: find out why this sometimes doesnt find all positions
            //Logger.log(LOGGER_LEVEL.TEMP_DEBUG, this.toString() + " current: " + p);
            if (!(targetPositions.contains(p))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        final String suffix;
        if (this instanceof LocalPlayer) {
            suffix = "H";
        } else if (this instanceof ComputerPlayer) {
            suffix = "C";
        } else {
            suffix = "N";
        }
        return this.name + "[" + suffix + "]";

    }
}
