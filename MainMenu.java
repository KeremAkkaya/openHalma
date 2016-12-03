import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MainMenu extends JFrame {
    private JButton buttonClose;
    private JComboBox comboBoxPlayer1;
    private JComboBox comboBoxPlayer2;
    private JComboBox comboBoxPlayer3;
    private JComboBox comboBoxPlayer4;
    private JComboBox comboBoxPlayer5;
    private JComboBox comboBoxPlayer6;
    private JButton buttonStart;
    private JPanel mainMenuPanel;
    private JTextField textFieldPlayer1;
    private JTextField textFieldPlayer2;
    private JTextField textFieldPlayer3;
    private JTextField textFieldPlayer4;
    private JTextField textFieldPlayer5;
    private JTextField textFieldPlayer6;
    private JButton button1;
    private JButton buttonColorPlayer1;
    private JButton buttonColorPlayer2;
    private JButton buttonColorPlayer3;
    private JButton buttonColorPlayer4;
    private JButton buttonColorPlayer5;
    private JButton buttonColorPlayer6;

    private final LinkedList<ComponentContainer> uiElements = new LinkedList<ComponentContainer>() {{
        add(new ComponentContainer(comboBoxPlayer1, textFieldPlayer1, buttonColorPlayer1));
        add(new ComponentContainer(comboBoxPlayer2, textFieldPlayer2, buttonColorPlayer2));
        add(new ComponentContainer(comboBoxPlayer3, textFieldPlayer3, buttonColorPlayer3));
        add(new ComponentContainer(comboBoxPlayer4, textFieldPlayer4, buttonColorPlayer4));
        add(new ComponentContainer(comboBoxPlayer5, textFieldPlayer5, buttonColorPlayer5));
        add(new ComponentContainer(comboBoxPlayer6, textFieldPlayer6, buttonColorPlayer6));
    }};

    private ActionListener actionListenerColor;


    private enum PLAYER_VALUES {
        EMPTY_PLAYER("-"),
        HUMAN_PLAYER("Human"),
        COMPUTER_PLAYER("Computer");

        private final String s;

        PLAYER_VALUES(String s) {
            this.s = s;
        }

        public String toString() {
            return s;
        }
    }

    private class ComponentContainer {
        public final JTextField textField;
        public final JComboBox comboBox;
        public final JButton colorButton;

        public ComponentContainer(JComboBox comboBox, JTextField textField, JButton colorButton) {
            this.comboBox = comboBox;
            this.textField = textField;
            this.colorButton = colorButton;
        }
    }

    private class GameThread extends Thread {
        private final Game game;

        public GameThread(Game game) {
            this.game = game;
        }

        public void run() {
            game.start();
        }
    }
    public MainMenu() {

        super("openHalma");

        /*buttonColorPlayer1 = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.setColor(Color.GREEN);
                graphics.fillRect(0, 0, getSize().width, getSize().height);
            }
        };*/

        actionListenerColor = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JButton source = ((JButton) (actionEvent.getSource()));
                Color current = source.getBackground();
                Color newColor = JColorChooser.showDialog(null, "Change color", current);
                if (newColor != null) {
                    source.setBackground(newColor);
                }
            }
        };

        Logger.log(LOGGER_LEVEL.GUI_DEBUG, "Setting up GUI");
        for (ComponentContainer c : uiElements) {
            c.comboBox.addItem(PLAYER_VALUES.EMPTY_PLAYER);
            c.comboBox.addItem(PLAYER_VALUES.HUMAN_PLAYER);
            c.comboBox.addItem(PLAYER_VALUES.COMPUTER_PLAYER);
            c.comboBox.setSelectedItem(PLAYER_VALUES.EMPTY_PLAYER);
            c.colorButton.addActionListener(actionListenerColor);
        }
        comboBoxPlayer1.setSelectedItem(PLAYER_VALUES.HUMAN_PLAYER);
        comboBoxPlayer2.setSelectedItem(PLAYER_VALUES.COMPUTER_PLAYER);
        textFieldPlayer1.setText("Human");
        textFieldPlayer2.setText("Fred");

        buttonClose.addActionListener(e -> System.exit(0));
        buttonStart.addActionListener(e -> start());


        setContentPane(mainMenuPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Logger.log(LOGGER_LEVEL.GUI_DEBUG, "Done setting up");

        JColorChooser colorChooser = new JColorChooser();
        colorChooser.setVisible(true);
    }

    private void start() {
        LinkedList<Player> players = new LinkedList<>();

        for (ComponentContainer c : uiElements) {
            switch (((PLAYER_VALUES) c.comboBox.getSelectedItem())) {
                case EMPTY_PLAYER:
                    break;
                case COMPUTER_PLAYER:
                    players.add(new ComputerPlayer(FIELD_VALUE.getValByInt(players.size()), c.colorButton.getBackground(), c.textField.getText(), AI.STRATEGY.MINIMAX, 6));
                    break;
                case HUMAN_PLAYER:
                    players.add(new LocalPlayer(FIELD_VALUE.getValByInt(players.size()), c.colorButton.getBackground(), c.textField.getText()));
                    break;
            }
        }
        StarBoard board = BoardFactory.createStandardStarBoard(9, players, 0);
        GameThread gameThread = new GameThread(new Game(board, players));
        this.setVisible(false);
        gameThread.start();

        //this.setVisible(false);
    }
}
