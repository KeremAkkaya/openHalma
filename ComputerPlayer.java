import java.util.*;
import java.awt.Color;

public class ComputerPlayer extends Player
{
    AI.STRATEGY strategy;
    int depth;
    //for the computerplayer the next move is chosen by an ai
    public ComputerPlayer(FIELD_VALUE fv, Color color, String s, AI.STRATEGY strat, int depth) {
        super(fv, color, s);
        this.depth = depth;
        this.strategy = strat;
    }

    public Move requestMove(StarBoard board, LinkedList<Player> pl, Player p) {
        return AI.move(board, this.depth, pl, strategy, this);
    }
    
}
