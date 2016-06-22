package oss.core;


import java.awt.Color;
/**
 * Enumeration class Field - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum FIELD_VALUE
{
    JUMP(-3, ','), INVALID(-2, ' '), EMPTY(-1, '.'), PLAYER1(0, '+'), PLAYER2(1, '#'), PLAYER3(2, '*'), PLAYER4(3, '&'), PLAYER5(4, '%'), PLAYER6(5, '§');
    private final int val;
    private final char c; //only needed for debug text interface, might as well leave it here
    private Player player = null;
    
    FIELD_VALUE(int val, char c) {
        this.val = val;
        this.c = c;
    }
    
    public int getVal() {
        return val;
    }
    
    public char getSymbol() {
        return c;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player p) {
        this.player = p;
    }
    
    public static FIELD_VALUE getValByInt(int i) {
        for (FIELD_VALUE fv : FIELD_VALUE.values())  {
            if (fv.getVal() == i) return fv;
        }
        return null;
    }
}
