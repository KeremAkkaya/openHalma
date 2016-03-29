
/**
 * Write a description of class Move here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Move
{
    public final Player player;
    public final Position start, end;
    public Move(Player p, Position start, Position end)
    {
        this.player = p;
        this.start = start;
        this.end = end;
    }

    
}
