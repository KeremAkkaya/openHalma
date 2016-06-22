package oss.core;

/**
 * Write a description of class Position here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Position
{
    public static final Position InvalidPosition = new Position(-1, -1);
    public final int x, y;
    
    public Position()
    {
        this(0,0);
    }
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object p) {
        if ( (((Position)p).x == this.x) && (((Position)p).y == this.y)) return true;
        return false;
    }
    
    public int hashCode() {
        return 1;
    }
    
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
