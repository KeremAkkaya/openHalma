
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
    
    public Player() {
        this(FIELD_VALUE.PLAYER1,null);
    }
    
    public Player(FIELD_VALUE fieldValue, Color color) {
        if(fieldValue.getVal() < 0) System.out.println("invalid field value for player creation");
        this.fieldValue = fieldValue;
        this.p_color = (color == null) ? Color.black : color;
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
}
