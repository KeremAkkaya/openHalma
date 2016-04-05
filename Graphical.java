
/**
 * Write a description of class Graphical here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.*;

public class Graphical extends JFrame implements Interface     //interface as interface
{
    /**
     * Added by Eclipse, JPanel seems to implement java.io.Serializable --> The serialization runtime associates with each serializable class a version number, called a serialVersionUID, which is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to serialization.
     */
    private static final long serialVersionUID = 1L;
    private static final int CIRCLE_RADIUS = 25;
    private static final double CIRCLE_DISTANCE = 2.5 * CIRCLE_RADIUS;//25; //distance center to center, should be > 2*CIRCLE_RADIUS
    private static final int CIRCLE_FRAME = ((int)Math.round(CIRCLE_RADIUS / 8.0));
    private static final double PADDING_VERTICAL = CIRCLE_DISTANCE * Math.sqrt(3.0 / 4.0);
    private static final double PADDING_HORIZONTAL = CIRCLE_DISTANCE / 2;
    private static final double CONSTANT_HORIZONTAL = -6 * CIRCLE_DISTANCE;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int PADDING_BORDER = 40;
    private static final Color BACKGROUND_COLOR = Color.white;
    private static final Color FRAME_COLOR = Color.black;
    private static final Color JUMP_COLOR = Color.LIGHT_GRAY;
    private static final Color SELECTED_COLOR = Color.red;

    private class Panel extends JPanel {
        private Board board = null;
        private Position hoverPosition = new Position(-1,-1);
        private Position selectedPosition = new Position(-1,-1);
        //        private final Game game;
        private LinkedList<Position> possibleJumps = new LinkedList<>();

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
            Position pos;

            for (int x = 0; x < dimension; x++) {
                for (int y = 0; y < dimension; y++) {
                    fv = board.getPosition(x, y);
                    if (fv != FIELD_VALUE.INVALID) {
                        c = fv.getPlayer().getColor();
                        if (new Position(x, y).equals(selectedPosition)) {
                            drawFieldCentered(g, c, SELECTED_COLOR, (getCoordinateX(x,y)), (getCoordinateY(y)));
                        } else if (possibleJumps.contains(new Position(x, y))) {
                            drawFieldCentered(g, JUMP_COLOR, FRAME_COLOR, (getCoordinateX(x,y)), (getCoordinateY(y)));
                        } else {
                            drawFieldCentered(g, c, FRAME_COLOR, (getCoordinateX(x,y)), (getCoordinateY(y)));
                        }
                    }
                }
            }
        }

        protected int getCoordinateX(int x, int y) {
            return (int)Math.round( PADDING_BORDER + (y * PADDING_HORIZONTAL) + (x * CIRCLE_DISTANCE) + CONSTANT_HORIZONTAL );
        }

        protected int getCoordinateY(int y) {
            return (int)Math.round( PADDING_BORDER + (y * PADDING_VERTICAL) );
        }

        protected Position getPositionByCoordinates(int x, int y) {
            int exacty = ((int)Math.round((y - PADDING_BORDER) / PADDING_VERTICAL));
            int exactx = ((int)Math.round( (x - (PADDING_BORDER + (exacty * PADDING_HORIZONTAL) + CONSTANT_HORIZONTAL)) / CIRCLE_DISTANCE ));
            int distancex = Math.abs(x - getCoordinateX(exactx, exacty));
            int distancey = Math.abs(y - getCoordinateY(exacty));
            if (Math.round(Math.sqrt(distancex * distancex + distancey * distancey)) <= CIRCLE_RADIUS) return new Position(exactx, exacty);
            return Position.InvalidPosition;
        }

        private void printvar() {
            System.out.println("CR: " + CIRCLE_RADIUS);
            System.out.println("CD: " + CIRCLE_DISTANCE);
            System.out.println("PB: " + PADDING_BORDER);
            System.out.println("PH: " + PADDING_HORIZONTAL);
            System.out.println("PV: " + PADDING_VERTICAL);
            System.out.println("SQRT: " + Math.sqrt(3.0 / 4.0));
            System.out.println("CH: " + CONSTANT_HORIZONTAL);
        }

        protected void drawFieldCentered(Graphics g, Color center, Color frame, int x, int y) {
            drawCenteredCircle(g, frame, x, y, CIRCLE_RADIUS);
            drawCenteredCircle(g, center, x, y, CIRCLE_RADIUS - CIRCLE_FRAME);
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

        public void updatePosition(int x, int y) {
            //System.out.println(x + ";" + y);
            Position pos = getPositionByCoordinates(x,y);
            //System.out.println(pos.toString());
            if (!this.hoverPosition.equals(pos)) {
                hoverPosition = pos;
                //System.out.println(hoverPosition.toString());
                hover();
            }
        }

        private boolean validHover() {
            return (!hoverPosition.equals(Position.InvalidPosition));
        }

        private void hover() {
            //System.out.println("selected" + selected);
            //System.out.println("valid: " + validHover());
            boolean selected = !selectedPosition.equals(Position.InvalidPosition);
            if (!selected && (validHover())) {
                //System.out.println(game.getNextPlayer);
                possibleJumps = board.getJumpPositions(hoverPosition);
            } else if (selected) {
                possibleJumps = board.getJumpPositions(selectedPosition);
            } else {
                selectedPosition = Position.InvalidPosition;
                possibleJumps.clear();
            }
            repaint();
        }

        public void click() {
            if (game.getNextPlayer() instanceof LocalPlayer) {
                if (validHover()) {
                    if (hoverPosition.equals(selectedPosition)) {
                        selectedPosition = Position.InvalidPosition;
                    } else {
                        selectedPosition = hoverPosition;
                    }
                }
            } else {
                selectedPosition = Position.InvalidPosition;
            }
            hover();
        }
    }

    private class ClickListener implements MouseListener {
        private final Panel panel;
        public ClickListener(Panel p) {
            this.panel = p;
        }

        public void mouseClicked(MouseEvent e) {
            panel.click();
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

    }

    private class MoveListener implements MouseMotionListener {
        private final Panel panel;
        public MoveListener(Panel p) {
            this.panel = p;
        }

        public void mouseMoved(MouseEvent e) {
            panel.updatePosition(e.getX(), e.getY());
        }

        public void mouseDragged(MouseEvent e) {

        }
    }

    private Game game;
    private final Panel panel;

    public Graphical()
    {
        super("openHalma");
        setSize(WIDTH,HEIGHT);
        panel = new Panel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.addMouseMotionListener(new MoveListener(panel));
        panel.addMouseListener(new ClickListener(panel));
        add(panel);
        setVisible(true);
    }

    public void setGame(Game game) {
        this.game = game;

    }

    public void printBoard(Board b)  {
        panel.setBoard(b);
        panel.repaint();
    }

    public Move requestMove(Player p) {
        return null;
    }

}
