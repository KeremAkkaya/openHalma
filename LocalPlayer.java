import java.awt.Color;
import java.util.*;

public class LocalPlayer extends Player
{
    public LocalPlayer(FIELD_VALUE fieldValue, Color color, String name) {
        super(fieldValue, color, name);
    }

    public Move requestMove(StarBoard board, LinkedList<Player> pl, Player p) {
        return Move.nullMove;
    }
}
