import java.util.LinkedList;

/**
 * Write a description of class _test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class _test
{
	static StarBoard sb;
    static String savedBoard;
    
    public static void main(String[] args){
    	//makeTest();
        guiTest();
    }
       
    public static void makeTest()
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
        System.out.println(sb.toString());
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

    public static void guiTest() {
    	LinkedList<Player> plist = new LinkedList<>();
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER1, null));
        Player p2 = new LocalPlayer(FIELD_VALUE.PLAYER2, null);
        plist.add(p2);
    	StarBoard board = BoardFactory.createStandardStarBoard(17,plist,0);;
    	Interface i = new Graphical();
    	i.printBoard(board);
    }
}
