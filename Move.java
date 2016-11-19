public class Move
{
    public final Player player;
    public final Position start, end;
    public static Move nullMove = new Move(Player.emptyPlayer, Position.InvalidPosition, Position.InvalidPosition);
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
    
    public boolean equals(Object m) {
        return (((Move) m).player == this.player) && (((Move) m).start == this.start) && (((Move) m).end == this.end);
    }
}
