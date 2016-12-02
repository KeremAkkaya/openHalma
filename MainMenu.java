
import sun.awt.image.ImageWatched;

import javax.swing.*;
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

    private final LinkedList<componentContainer> uiElements = new LinkedList<componentContainer>() {{
        add(new componentContainer(comboBoxPlayer1, textFieldPlayer1));
        add(new componentContainer(comboBoxPlayer2, textFieldPlayer2));
        add(new componentContainer(comboBoxPlayer3, textFieldPlayer3));
        add(new componentContainer(comboBoxPlayer4, textFieldPlayer4));
        add(new componentContainer(comboBoxPlayer5, textFieldPlayer5));
        add(new componentContainer(comboBoxPlayer6, textFieldPlayer6));
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

    private class componentContainer {
        public final JTextField textField;
        public final JComboBox comboBox;

        public componentContainer(JComboBox comboBox, JTextField textField) {
            this.comboBox = comboBox;
            this.textField = textField;
        }
    }

    public MainMenu() {
        super("openHalma");
        Logger.log(LOGGER_LEVEL.GUI_DEBUG, "Setting up GUI");
        for (componentContainer c : uiElements) {
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

        for (componentContainer c : uiElements) {
            if (c.comboBox.getSelectedItem() != PLAYER_VALUES.EMPTY_PLAYER) {

            }
        }

        //this.setVisible(false);
    }
}
