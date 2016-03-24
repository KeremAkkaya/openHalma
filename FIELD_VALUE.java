
/**
 * Enumeration class Field - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum FIELD_VALUE
{
    INVALID(2),EMPTY(-1),PLAYER1(0),PLAYER2(1),PLAYER3(2),PLAYER4(3),PLAYER5(4),PLAYER6(5);
    private int val;
    
    FIELD_VALUE(int val) {
        this.val = val;
    }
    
    public int getVal() {
        return val;
    }
}
