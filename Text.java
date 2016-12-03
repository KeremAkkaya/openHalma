import java.io.*;

public class Text implements Interface
{
    
    private Game game = null;

    public Text()
    {

    }

    public void repaint() {
        System.out.println(game.board.toString(false));
    }

    public Move requestMove(Player player) {
        System.out.println("Enter xstart <Enter>, ystart <Enter>, xtarget <Enter>, ytarget <Enter>\nwill crash on string, empty, etc...");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int xstart, ystart, xtarget, ytarget;
        try {
            xstart = Integer.parseInt(br.readLine());
            ystart = Integer.parseInt(br.readLine());
            xtarget = Integer.parseInt(br.readLine());
            ytarget = Integer.parseInt(br.readLine());
            return new Move(player, new Position(xstart, ystart), new Position(xtarget, ytarget));
        } catch (Exception e) {
            System.out.println("smth. failed");
        }
        return null;
    }
    
    public void setGame(Game g) {
        this.game = g;
    }
}
