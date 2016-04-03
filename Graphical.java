
/**
 * Write a description of class Graphical here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Graphical extends JFrame implements Interface     //interface as interface
{
    /**
     * Added by Eclipse, JPanel seems to implement java.io.Serializable --> The serialization runtime associates with each serializable class a version number, called a serialVersionUID, which is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to serialization.
     */
    private static final long serialVersionUID = 1L;
    private static final int CIRCLE_RADIUS = 50;
    private static final int CIRCLE_DISTANCE = 25; //distance center to center
    private static final int CIRCLE_FRAME = 4;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final Color BACKGROUND_COLOR = Color.white;
    private static final Color FRAME_COLOR = Color.black;
    private final Panel panel;

    private static class Panel extends JPanel {
        public Panel() {
            super();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawFieldCentered(g, Color.blue, 0, 0);
        }

        protected void drawFieldCentered(Graphics g, Color c, int x, int y) {
            drawCenteredCircle(g, FRAME_COLOR, x, y, CIRCLE_RADIUS);
            drawCenteredCircle(g, c, x, y, CIRCLE_RADIUS - CIRCLE_FRAME);
        }

        protected void drawCenteredCircle(Graphics g, Color c, int x, int y, int radius) {
            x = x - radius;
            y = y - radius;
            g.setColor(c);
            g.fillOval(x, y, 2*radius, 2*radius);
            System.out.println(x + " " + y + " " + radius);
        }
    }

    public Graphical()
    {
        super("openHalma");
        setSize(WIDTH,HEIGHT);

        panel = new Panel();
        panel.setBackground(BACKGROUND_COLOR);
        add(panel);
        setVisible(true);
    }

    public void printBoard(Board b)  {

    }

    private void drawCircleAt(int x, int y, int radius, Color color) {

    }

    public Move requestMove(Player p) {
        return null;
    }
}
