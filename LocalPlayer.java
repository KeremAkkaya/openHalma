import java.awt.Color;
import java.util.*;

public class LocalPlayer extends Player
{
    public LocalPlayer(FIELD_VALUE fieldValue, Color color) {
        super(fieldValue, color);
    }

    public Move requestMove(StarBoard board, LinkedList<Player> pl, Player p) {
        return Move.nullMove;
    }
}
