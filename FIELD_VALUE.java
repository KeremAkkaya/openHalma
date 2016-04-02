import java.awt.Color;
/**
 * Enumeration class Field - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum FIELD_VALUE
{
    INVALID(-2, ' ', Color.white),EMPTY(-1, '.', Color.white),PLAYER1(0, '+', Color.red),PLAYER2(1, '#', Color.blue),PLAYER3(2, '*', Color.black),PLAYER4(3, '&', Color.green),PLAYER5(4, '%', Color.yellow),PLAYER6(5, '§', Color.pink);
    private final int val;
    private final char c;
    private final Color color;
    FIELD_VALUE(int val, char c, Color color) {
        this.val = val;
        this.c = c;
        this.color = color;
    }
    
    public int getVal() {
        return val;
    }
    
    public char getSymbol() {
        return c;
    }
    
    public static FIELD_VALUE getValByInt(int i) {
        for (FIELD_VALUE fv : FIELD_VALUE.values())  {
            if (fv.getVal() == i) return fv;
        }
        return null;
    }
    
    public Color getColor() {
        return color;
    }
    //add a symbol return? mayyybeeeee
}
