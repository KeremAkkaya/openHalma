
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
        return (((Position) p).x == this.x) && (((Position) p).y == this.y);
    }
    
    public int hashCode() {
        return 1;
    }
    
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
