package Game.Towers;

import Game.Bar;
import Game.CustomButton;
import Game.GameWindow;

import javax.swing.*;
import java.awt.*;


public class TowerAddBar extends Bar {
    private GameWindow gameWindow;
    public CustomButton[] towerButton = new CustomButton[4];
    private ImageIcon towerImagePath;
    private Image[] towerImage = new Image[4];
    private boolean isButtonPressed;
    private boolean isBarShown;

    public TowerAddBar(GameWindow gameWindow, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.gameWindow = gameWindow;
        initButtons();
    }

    private void initButtons() {
        int offset_x =  37;

        for(int i = 0; i < towerButton.length; i++) {
            towerButton[i] = new CustomButton("", x + 18 + offset_x * i, (y + height/2) - 16, 32, 32, i);
            towerImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/tower" +i+".png")));
            towerImage[i] = towerImagePath.getImage();
        }
    }

    public void paint(Graphics g) {
        if(isBarShown() && !isButtonPressed()) {
            g.setColor(new Color(188, 108, 37));
            g.fillRect(x, y, width, height);

            g.setColor(Color.black);
            g.drawRect(x, y, width, height);

            for (int i = 0; i < towerButton.length; i++) {
                g.fillRect(towerButton[i].x, towerButton[i].y, towerButton[i].width, towerButton[i].height);
                towerButton[i].paint(g, towerImage[i]);
                paintButton(g, towerButton[i]);
            }
        }
    }


    public boolean isButtonPressed() {
        return isButtonPressed;
    }

    public void setButtonPressed(boolean buttonPressed) {
        isButtonPressed = buttonPressed;
    }

    public boolean isBarShown() {
        return isBarShown;
    }

    public void setBarShown(boolean barShown) {
        isBarShown = barShown;
    }


}

