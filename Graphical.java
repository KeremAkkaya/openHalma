
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Graphical extends JFrame implements Interface
{
    private static final long serialVersionUID = 1L;
    private static final int CIRCLE_RADIUS = 15;
    private static final double CIRCLE_DISTANCE = 2.5 * CIRCLE_RADIUS;//25; //distance center to center, should be > 2*CIRCLE_RADIUS
    private static final int CIRCLE_FRAME = ((int)Math.round(CIRCLE_RADIUS / 8.0));
    private static final double PADDING_VERTICAL = CIRCLE_DISTANCE * Math.sqrt(3.0 / 4.0);
    private static final double PADDING_HORIZONTAL = CIRCLE_DISTANCE / 2;
    private double CONSTANT_HORIZONTAL = 0;// - 4 * CIRCLE_DISTANCE - CIRCLE_RADIUS;
    private static final int PADDING_BORDER = 40;
    private static final Color BACKGROUND_COLOR = Color.white;
    private static final Color FRAME_COLOR = Color.black;
    private static final Color JUMP_COLOR = Color.LIGHT_GRAY;
    private static final Color SELECTED_COLOR = Color.red;

    private class Panel extends JPanel {
        private StarBoard board = null;
        private Position hoverPosition = Position.InvalidPosition;

        public Panel() {
            super();
        }

        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            if (board == null) return;
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0,0,WIDTH,HEIGHT);
            Logger.log(LOGGER_LEVEL.GRAPHICAL_DEBUG, "Redrawing!");

            for (Position arrayPos : board.getAllPositions()) {
                FIELD_VALUE fv = board.getPosition(arrayPos);
                Position visualPos = getVisualPosition(arrayPos);
                if (fv != FIELD_VALUE.INVALID) {
                    Color c = fv.getPlayer().getColor();
                    if (arrayPos.equals(game.getSelectedPosition())) {
                        drawFieldCentered(g, c, SELECTED_COLOR, visualPos);
                    } else if ((game.getPossibleJumps().contains(arrayPos)) && (fv == FIELD_VALUE.EMPTY)) {
                        drawFieldCentered(g, JUMP_COLOR, FRAME_COLOR, visualPos);
                    } else {
                        drawFieldCentered(g, c, FRAME_COLOR, visualPos);
                    }
                } else {
                    //drawFieldCentered(g, Color.BLACK, Color.black, pos.x, pos.y);
                    //debug output
                }
            }
        }

        protected Position getVisualPosition(Position pos) {
            int x = (int) Math.round(PADDING_BORDER + (pos.y * PADDING_HORIZONTAL) + (pos.x * CIRCLE_DISTANCE) - (((this.board.getDimension() - 1) / 3) * CIRCLE_DISTANCE + 2 * CIRCLE_RADIUS));
            int y = (int) Math.round(PADDING_BORDER + (pos.y * PADDING_VERTICAL));
            return new Position(x, y);
        }

        protected Position getPositionByCoordinates(int x, int y) {

            int exacty = ((int)Math.round((y - PADDING_BORDER) / PADDING_VERTICAL));
            int exactx = ((int)Math.round( (x - (PADDING_BORDER + (exacty * PADDING_HORIZONTAL) - (((this.board.getDimension() - 1) /  3) * CIRCLE_DISTANCE + 2* CIRCLE_RADIUS))) / CIRCLE_DISTANCE ));
            Position p = getVisualPosition(new Position(exactx, exacty));
            int distancex = Math.abs(x - p.x);
            int distancey = Math.abs(y - p.y);
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

        protected void drawFieldCentered(Graphics g, Color center, Color frame, Position p) {
            drawCenteredCircle(g, frame, p, CIRCLE_RADIUS);
            drawCenteredCircle(g, center, p, CIRCLE_RADIUS - CIRCLE_FRAME);
        }

        protected void drawCenteredCircle(Graphics g, Color c, Position p, int radius) {
            g.setColor(c);
            g.fillOval(p.x - radius, p.y - radius, 2 * radius, 2 * radius);
        }

        public void setBoard(StarBoard board) {
            this.board = board;
        }

        public void updatePosition(int x, int y) {
            Position pos = getPositionByCoordinates(x,y);
            if (!this.hoverPosition.equals(pos)) {
                hoverPosition = pos;
                game.hoverPosition(hoverPosition);
            }
        }

        public void click() {
           game.click();
        }
    }

    private class ClickMoveListener implements MouseListener, MouseMotionListener {
        private final Panel panel;
        int previousX, previousY;

        private double _distance(MouseEvent e) {
            return Math.sqrt(Math.pow(e.getX() - previousX, 2) + Math.pow(e.getY() - previousY, 2));
        }

        public ClickMoveListener(Panel p) {
            this.panel = p;
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
            previousX = e.getX();
            previousY = e.getY();
        }

        public void mouseReleased(MouseEvent e) {
            if (_distance(e) < 5) panel.click();
        }

        public void mouseMoved(MouseEvent e) {
            panel.updatePosition(e.getX(), e.getY());
        }

        public void mouseDragged(MouseEvent e) {
        }
    }

    private Game game;
    private final Panel panel;

    public Graphical(Game game)
    {
        super("openHalma");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new Panel();
        panel.setBackground(BACKGROUND_COLOR);
        ClickMoveListener clickMoveListener = new ClickMoveListener(panel);
        panel.addMouseMotionListener(clickMoveListener);
        panel.addMouseListener(clickMoveListener);
        add(panel);
        setVisible(true);
        this.game = game;
        panel.setBoard(game.board);

        int dim = game.board.getDimension();
        int short_tri = (dim - 1) / 3;
        int full = dim - 1;
        Position a, b;
        a = panel.getVisualPosition(new Position(full, short_tri));
        b = panel.getVisualPosition(new Position(short_tri, full));
        int x = a.x;
        int y = b.y;
        x += CIRCLE_RADIUS + PADDING_BORDER;
        y += CIRCLE_RADIUS + 1.5 * PADDING_BORDER;
        this.setSize(x, y);
        panel.setSize(x, y);
    }

    public void repaint()  {
        panel.repaint();
    }
}
