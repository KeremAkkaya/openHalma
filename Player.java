import java.util.*;
import java.awt.Color;

/**
 * Abstract class Player - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Player
{
    protected Color p_color;
    protected FIELD_VALUE fieldValue;
    public static Player emptyPlayer = new LocalPlayer(FIELD_VALUE.EMPTY,Color.white);
    protected String name;
    protected LinkedList<Position> currentPositions, targetPositions;
    protected Position tip;
    protected Position direction; //this is a position because it needs to hold x and y value

    public Player() {
        this(FIELD_VALUE.PLAYER1, null, "");
    }

    public Player (FIELD_VALUE fieldValue) {
        this(fieldValue, null, "");
    }

    public Player(FIELD_VALUE fieldValue, Color color) {
        this(fieldValue, color, "");
    }

    public Player(FIELD_VALUE fieldValue, String s) {
        this(fieldValue, null, s);
    }

    public Player(FIELD_VALUE fieldValue, Color color, String s) {
        setFieldValue(fieldValue);
        this.p_color = (color == null) ? Color.black : color;
        this.name = (s.equals("")) ? "Player" + fieldValue.getVal() : s;
    }

    public void initPositions(Board board) {
        for(Position p: currentPositions) {
            board.setPosition(p, this.fieldValue);
        }
        double distance = 0, pdistance;
        Position center = new Position(board.dimension / 2, board.dimension / 2);
        for (Position p: targetPositions) {
            pdistance = AI.pointDistance(p, center, board);
            if (pdistance > distance) {
                distance = pdistance;
                tip = p;
            }

        }
        System.out.println(tip);
    }

    public void setCurrentPositions(LinkedList<Position> p) {
        this.currentPositions=p;
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

    public void setName(String s) {
        this.name = s;
    }

    public void setFieldValue(FIELD_VALUE fv) {
        fv.setPlayer(this);
        this.fieldValue = fv;
    }

    public void moveToken(Move move) {
        currentPositions.remove(move.start);
        currentPositions.add(move.end);
    }

    public void setColor(Color c) {
        this.p_color = c;
    }

    //request the player to make a move
    public abstract Move requestMove(Board board, LinkedList<Player> pl, Player p);

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

    public LinkedList<Position> getCurrentPositions() {
        return currentPositions;
    }

    public boolean isFinished() {
        for (Position p: currentPositions) {
            if (!(targetPositions.contains(p))) {
                return false;
            }
        }
        return true;
    }
}
