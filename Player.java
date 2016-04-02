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
    protected final Color p_color;
    protected final FIELD_VALUE fieldValue;
    public static Player emptyPlayer = new LocalPlayer(FIELD_VALUE.EMPTY,null);
    protected String name;
    protected LinkedList<Position> tokens;
    
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
        this.fieldValue = fieldValue;
        this.p_color = (color == null) ? Color.black : color;
        this.name = (s.equals("")) ? "Player" + fieldValue.getVal() : s;
    }

    public char getSymbol() {
        return fieldValue.getSymbol();
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
    
    public abstract Move requestMove(Interface iface);
    
    public boolean equals (Player p) {
        return p.fieldValue == this.fieldValue;
    }
}
