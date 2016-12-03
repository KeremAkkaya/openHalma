
public class Position
{
    public static final Position InvalidPosition = new Position(-1, -1);
    public final int x, y;

    public Position() { // create invalid position by default
        this(-1, -1);
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
