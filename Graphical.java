
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
    private static final int CIRCLE_RADIUS = 25;
    private static final double CIRCLE_DISTANCE = 2.5 * CIRCLE_RADIUS;//25; //distance center to center, should be > 2*CIRCLE_RADIUS
    private static final int CIRCLE_FRAME = CIRCLE_RADIUS / 8;
    private static final double PADDING_VERTICAL = CIRCLE_DISTANCE * Math.sqrt(3 / 4);
    private static final double PADDING_HORIZONTAL = CIRCLE_DISTANCE / 2;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int PADDING_BORDER = 40;
    private static final Color BACKGROUND_COLOR = Color.white;
    private static final Color FRAME_COLOR = Color.black;
    private final Panel panel;

    private static class Panel extends JPanel {
        
        private Board board = null;
        
        public Panel() {
            super();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (board == null) return;
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0,0,WIDTH,HEIGHT);
            int dimension = board.getDimension();
            Color c;
            FIELD_VALUE fv;
            double xpos, ypos;
            
            for (int x = 0; x < dimension; x++) {
                for (int y = 0; y < dimension; y++) {
                    fv = board.getPosition(x, y);
                    if (fv != FIELD_VALUE.INVALID) {
                        c = fv.getColor();
                        xpos = PADDING_BORDER;
                        //xpos += y * PADDING_HORIZONTAL;
                        xpos += y * (PADDING_HORIZONTAL + 2 * CIRCLE_RADIUS);
                        ypos = PADDING_BORDER;
                        ypos += x * (PADDING_VERTICAL + 2 * CIRCLE_RADIUS);
                        drawFieldCentered(g, c, ((int) xpos), ((int) ypos));
                    }
                }
            }
        }

        protected void drawFieldCentered(Graphics g, Color c, int x, int y) {
            drawCenteredCircle(g, FRAME_COLOR, x, y, CIRCLE_RADIUS);
            drawCenteredCircle(g, c, x, y, CIRCLE_RADIUS - CIRCLE_FRAME);
        }

        protected void drawCenteredCircle(Graphics g, Color c, int x, int y, int radius) {
            x = x - radius;
            y = y - radius;
            g.setColor(c);
            g.fillOval(x, y, 2 * radius, 2 * radius);
        }
        
        public void setBoard(Board board) {
            this.board = board;
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
        panel.setBoard(b);
        panel.repaint();
    }

    public Move requestMove(Player p) {
        return null;
    }
}
