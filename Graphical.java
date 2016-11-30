
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
            int dimension = board.getDimension();
            Color c;
            FIELD_VALUE fv;
            Position pos;
            Logger.log(LOGGER_LEVEL.GRAPHICAL_DEBUG, "Redrawing!");

            for (int x = 0; x < dimension; x++) {
                for (int y = 0; y < dimension; y++) {
                    fv = board.getPosition(x, y);
                    pos = new Position(getCoordinateX(x,y), (getCoordinateY(y)));
                    if (fv != FIELD_VALUE.INVALID) {
                        c = fv.getPlayer().getColor();
                        if (new Position(x, y).equals(game.getSelectedPosition())) {
                            drawFieldCentered(g, c, SELECTED_COLOR, pos.x, pos.y);
                        } else if ((game.getPossibleJumps().contains(new Position(x, y))) && (fv == FIELD_VALUE.EMPTY)) {
                            drawFieldCentered(g, JUMP_COLOR, FRAME_COLOR,  pos.x, pos.y);
                        } else {
                            drawFieldCentered(g, c, FRAME_COLOR, pos.x, pos.y);
                        }
                    } else {
                        //drawFieldCentered(g, Color.BLACK, Color.black, pos.x, pos.y);
                        //debug output
                    }

                }
            }
        }

        protected int getCoordinateX(int x, int y) {
            return (int)Math.round( PADDING_BORDER + (y * PADDING_HORIZONTAL) + (x * CIRCLE_DISTANCE) - (((this.board.getDimension() - 1) /  3) * CIRCLE_DISTANCE + 2* CIRCLE_RADIUS) );
        }

        protected int getCoordinateY(int y) {
            return (int)Math.round( PADDING_BORDER + (y * PADDING_VERTICAL) );
        }

        protected Position getPositionByCoordinates(int x, int y) {
            int exacty = ((int)Math.round((y - PADDING_BORDER) / PADDING_VERTICAL));
            int exactx = ((int)Math.round( (x - (PADDING_BORDER + (exacty * PADDING_HORIZONTAL) - (((this.board.getDimension() - 1) /  3) * CIRCLE_DISTANCE + 2* CIRCLE_RADIUS))) / CIRCLE_DISTANCE ));
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

        public void setBoard(StarBoard board) {

            //CONSTANT_HORIZONTAL = 0 - ((board.getDimension() - 1) /  3) * CIRCLE_DISTANCE - CIRCLE_RADIUS;
            this.board = board;
            /*int x = board.getDimension();
            x = (3 * x + 1) / 4;
            int size = 0;
            size += 2 * PADDING_BORDER;
            size += x * CIRCLE_DISTANCE;
            size += CIRCLE_DISTANCE;
            this.setSize(size,size);//*/

            //CONSTANT_HORIZONTAL = 0 - ((this.board.getDimension() - 1) /  3) * CIRCLE_DISTANCE - CIRCLE_RADIUS; //uncommenting this gives me nullpointer
            //but why?

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
        panel.setBoard(game.getBoard());

        int dim = game.getBoard().getDimension();
        int short_tri = (dim - 1) / 3;
        int full = dim - 1;
        int x = panel.getCoordinateX(full, short_tri);
        int y = panel.getCoordinateY(full);
        x += CIRCLE_RADIUS + PADDING_BORDER;
        y += CIRCLE_RADIUS + 1.5 * PADDING_BORDER;
        this.setSize(x, y);
        panel.setSize(x, y);
    }

    public void repaint()  {
        panel.repaint();
    }
}
