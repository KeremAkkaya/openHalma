
/**
 * Write a description of class Position here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Position
{
    
    public final int x, y;
    
    public Position()
    {
        this(0,0);
    }
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position p) {
        if ((p.x == this.x) && (p.y == this.y)) return true;
        return false;
    }
}
