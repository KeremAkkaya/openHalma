
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

    public String toString() {
        String s = "";
        s += "Player" + (player.getFieldValue().getVal() + 1) + " from " + start.toString() + " to " + end.toString();
        return s;
    }
    
    public boolean equals(Move m) {
        if ((m.player == this.player) && (m.start == this.start) && (m.end == this.end)) return true;
        return false;
    }
}
