import java.util.LinkedList;
/**
 * Write a description of class _test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class _test
{
    
    public static void makeTest()
    {
        Interface i = new Text();
        LinkedList<Player> plist = new LinkedList<>();
        LocalPlayer p1 = new LocalPlayer(FIELD_VALUE.PLAYER1, null);
        //System.out.println(p1.getSymbol());
        plist.add(p1);
        StarBoard sb = BoardFactory.createStandardStarBoard(7,plist);
        i.printBoard(sb);
    }

    
}
