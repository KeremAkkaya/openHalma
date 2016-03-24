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

    public _test() {
        makeTest();
    }

    public void makeTest()
    {
        Interface i = new Text();
        LinkedList<Player> plist = new LinkedList<>();
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER1, null));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER2, null));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER3, null));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER4, null));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER5, null));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER6, null));
        sb = BoardFactory.createStandardStarBoard(16,plist,true);
        i.printBoard(sb);
    }

}
