import java.util.LinkedList;

/**
 * Write a description of class _test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class _test
{
    StarBoard sb;
    String savedBoard;
    public _test() {
        makeTest();
        //guiTest();
    }

    public void makeTest()
    {
        Interface i = new Text();
        LinkedList<Player> plist = new LinkedList<>();
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER1, null));
        Player p2 = new LocalPlayer(FIELD_VALUE.PLAYER2, null);
        plist.add(p2);
        /*plist.add(new LocalPlayer(FIELD_VALUE.PLAYER3, null));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER4, null));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER5, null));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER6, null));*/
        sb = BoardFactory.createStandardStarBoard(13,plist,0);
        System.out.println(sb.toString(false));
        LinkedList<Position> posis;
        posis = sb.getJumpPositions(new Position(9,4), p2);
        while (posis.peekFirst() != null) {
            System.out.println(posis.remove().toString());
        }
        
        savedBoard = sb.writeToString();
        StarBoard board2 = new StarBoard();
        //System.out.println(savedBoard);
        board2.readFromString(savedBoard);
        if (board2.equals(sb)) System.out.println("yay");
    }

    public void guiTest() {
    	StarBoard board = new StarBoard(10);
    }
}
