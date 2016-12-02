import javax.swing.*;
import java.awt.*;
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

    private final LinkedList<ComponentContainer> uiElements = new LinkedList<ComponentContainer>() {{
        add(new ComponentContainer(comboBoxPlayer1, textFieldPlayer1));
        add(new ComponentContainer(comboBoxPlayer2, textFieldPlayer2));
        add(new ComponentContainer(comboBoxPlayer3, textFieldPlayer3));
        add(new ComponentContainer(comboBoxPlayer4, textFieldPlayer4));
        add(new ComponentContainer(comboBoxPlayer5, textFieldPlayer5));
        add(new ComponentContainer(comboBoxPlayer6, textFieldPlayer6));
    }};


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

        public ComponentContainer(JComboBox comboBox, JTextField textField) {
            this.comboBox = comboBox;
            this.textField = textField;
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
        Logger.log(LOGGER_LEVEL.GUI_DEBUG, "Setting up GUI");
        for (ComponentContainer c : uiElements) {
            c.comboBox.addItem(PLAYER_VALUES.EMPTY_PLAYER);
            c.comboBox.addItem(PLAYER_VALUES.HUMAN_PLAYER);
            c.comboBox.addItem(PLAYER_VALUES.COMPUTER_PLAYER);
            c.comboBox.setSelectedItem(PLAYER_VALUES.EMPTY_PLAYER);
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

    }

    private void start() {
        LinkedList<Player> players = new LinkedList<>();

        for (ComponentContainer c : uiElements) {
            switch (((PLAYER_VALUES) c.comboBox.getSelectedItem())) {
                case EMPTY_PLAYER:
                    break;
                case COMPUTER_PLAYER:
                    players.add(new ComputerPlayer(FIELD_VALUE.getValByInt(players.size()), Color.red, c.textField.getText(), AI.STRATEGY.MINIMAX, 6));
                    break;
                case HUMAN_PLAYER:
                    players.add(new LocalPlayer(FIELD_VALUE.getValByInt(players.size()), Color.blue, c.textField.getText()));
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
