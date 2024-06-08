package Mainmenu;

import Game.GameWindow;
import Utils.Dimensions;

import javax.swing.*;
import java.awt.*;

//Mainmenu.Main Panel is the introduction panel, or in other words the main menu of the game
//which introduces the game with a few buttons for starting the game, giving info about the game is about,
//and to exit the game
public class Main extends JPanel {

    private JPanel menuPanel;
    private JPanel infoPanel;

    public Main() {
        JFrame frame = new JFrame("Tower defense");
        frame.setSize(Dimensions.WINDOW_WIDTH, Dimensions.WINDOW_HEIGHT);
        frame.setLocationRelativeTo((Component) null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        infoPanel = new InfoPanel();
        infoPanel.setVisible(false);
        infoPanel.setSize(Dimensions.WINDOW_WIDTH-20, Dimensions.WINDOW_HEIGHT-30);
        frame.add(infoPanel);

        menuPanel = this;
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.ipadx = Dimensions.WINDOW_WIDTH / 4;
        gbc.ipady = Dimensions.WINDOW_HEIGHT / 10;

        JButton gameStartButton = new JButton("Start Game");
        gameStartButton.setFont(new Font("Arial", Font.BOLD, 24));
        gameStartButton.addActionListener(event -> {
            frame.dispose();
            new GameWindow();
        });

        JButton informationButton = new JButton("Information");
        informationButton.setFont(new Font("Arial", Font.BOLD, 24));
        informationButton.addActionListener(event -> {
            menuPanel.setVisible(false);
            infoPanel.setVisible(true);
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.addActionListener(event -> {
            System.exit(0);
        });

        menuPanel.add(gameStartButton,gbc);
        menuPanel.add(informationButton,gbc);
        menuPanel.add(exitButton,gbc);

        frame.add(menuPanel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }

    class InfoPanel extends JPanel {
        public InfoPanel() {
            super();
            this.setLayout(new BorderLayout());

            //Title
            JLabel titleLabel = new JLabel("Information", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            this.add(titleLabel, BorderLayout.PAGE_START);

            //Game Info
            JTextArea gameInfoTextArea = new JTextArea("This game is a tower defense game, " +
                    "the objective of this game is to survive waves of enemies, " +
                    "each wave has a set amount of various enemies, " +
                    "your mission is to protect the castle from the incoming waves by " +
                    "building towers and upgrading them to be able to fight the waves of enemies. " +
                    "The castle can only sustain five hits before it collapses so make sure to defend it as much as possible. \n \n" +
                    "There are four different types of enemies which are determined by their color: \n" +
                    "1. Gray enemies: these are the normal enemies. \n" +
                    "2. Green enemies: these enemies regenerate health every 5 seconds. \n" +
                    "3.Red enemies: these enemies have more health than normal enemies. \n" +
                    "4. Blue enemies: these enemies are immune to damage for 2 seconds after being hit 5 times. \n \nThese enemies also come with three different sizes: \n" +
                    "1. Small Size: Small sized enemies are quicker but have less health. \n" +
                    "2. Normal Size: These are the normal-sized enemies and have no bonuses. \n" +
                    "3. Large Size: Large-sized enemies are slower but have more health. \n \n" +
                    "There are four different types of towers you can place to fight the wave of enemies and defend the castle: \n" +
                    "1. Mage Tower: This tower attacks one enemy every 3 seconds but can be upgraded to deal more damage and faster. \n" +
                    "2. Freeze Tower: This tower deals a small amount of damage every 3 seconds and slows all enemies within its range for 2 seconds. \n" +
                    "3. Bolder Tower: This tower throws a boulder towards the enemies within its range and can hit upto three enemies at once. \n" +
                    "4. Support Tower: This tower does no damage but if there's an enemy within its range it supports the towers within its range to attack faster, " +
                    "it supports the towers every 10 seconds.");
            gameInfoTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
            gameInfoTextArea.setEditable(false);
            gameInfoTextArea.setWrapStyleWord(true);
            gameInfoTextArea.setLineWrap(true);
            gameInfoTextArea.setBorder(BorderFactory.createCompoundBorder(
                    this.getBorder(),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            gameInfoTextArea.setOpaque(false);

            this.add(gameInfoTextArea, BorderLayout.CENTER);
            JPanel buttonPanel = new JPanel();
            JButton mainMenuButton = new JButton("Return");
            mainMenuButton.setFont(new Font("Arial", Font.BOLD, 20));
            mainMenuButton.addActionListener(event -> {
                this.setVisible(false);
                menuPanel.setVisible(true);
            });
            buttonPanel.add(mainMenuButton);
            this.add(buttonPanel, BorderLayout.SOUTH);
        }
    }
}