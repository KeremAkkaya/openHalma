import java.util.*;

public class NetworkPlayer extends Player
{

    public Move requestMove(Interface i, StarBoard board) {
        return null;
    }

    public Move requestMove(StarBoard board, LinkedList<Player> pl, Player p) {
        return Move.nullMove;
    }
}
