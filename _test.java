import java.util.LinkedList;
import java.awt.Color;

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
    LinkedList<Player> plist = new LinkedList<>();
    Game game;
    
    public static void main(String[] args) {
        new _test();
    }
    
    public _test() {
        main();
    }
    
    public void main(){
        //makeTest();
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER1, Color.blue));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER2, Color.red));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER3, Color.yellow));
        /*plist.add(new LocalPlayer(FIELD_VALUE.PLAYER3, Color.green));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER4, Color.orange));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER5, Color.magenta));
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER6, Color.yellow));*/
        
        guiTest();
    }
       
    public void makeTest()
    {
        Interface i = new Text();
        LinkedList<Player> plist = new LinkedList<>();
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER1, null));
        Player p2 = new LocalPlayer(FIELD_VALUE.PLAYER2, null);
        plist.add(p2);
        plist.add(new LocalPlayer(FIELD_VALUE.PLAYER3,null));
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

    public void guiTest() {
        /*StarBoard boards[] = new StarBoard[5];
        Graphical interfaces[] = new Graphical[5];
        Game games[] = new Game[5];
        int x=5;
        for(int i=0;i<4;i++) {
            boards[i] = BoardFactory.createStandardStarBoard(x+4*i,plist,0);
            interfaces[i] = new Graphical();
            games[i] = new Game(interfaces[i],boards[i]);
            games[i].addPlayer(plist.get(0));
            games[i].addPlayer(plist.get(1));
            games[i].addPlayer(plist.get(2));


            interfaces[i].repaint();
        }*/

        sb = BoardFactory.createStandardStarBoard(29,plist,0);
        if (sb==null) System.exit(55);
        Graphical i = new Graphical();
        Text t = new Text();
        game = new Game(i, sb);
        game.addPlayer(plist.get(0));
        game.addPlayer(plist.get(1));
        game.addPlayer(plist.get(2));
        t.setGame(game);
        i.setGame(game);
        t.repaint();
        i.repaint();
    }
}
