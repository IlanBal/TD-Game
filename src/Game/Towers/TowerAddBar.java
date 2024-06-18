package Game.Towers;

import Game.Bar;
import Game.CustomButton;
import Game.GameWindow;

import javax.swing.*;
import java.awt.*;

import static Utils.Types.Towers.GetTowerCost;
import static Utils.Types.Towers.GetTowerName;


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
            towerButton[i] = new CustomButton("", x + 18 + offset_x * i, (y + height/2) - 28, 32, 32, i);
            towerImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/tower" +i+".png")));
            towerImage[i] = towerImagePath.getImage();
        }
    }

    public void paint(Graphics g) {
        if(isBarShown() && !isButtonPressed()) {
            int textWidth, textHeight, textCost;

            g.setColor(new Color(188, 108, 37));
            g.fillRect(x, y, width, height);

            g.setColor(Color.black);
            g.drawRect(x, y, width, height);

            for (int i = 0; i < towerButton.length; i++) {
                g.setFont(new Font("Times New Roman", Font.BOLD, 14));
                if(towerButton[i].isMouseOver()) {
                    textWidth = g.getFontMetrics().stringWidth(GetTowerName(i));
                    textCost = g.getFontMetrics().stringWidth("Cost: " + GetTowerCost(i));
                    textHeight = g.getFontMetrics().getHeight();

                    if(gameWindow.getPlayerGold() >= GetTowerCost(i)) {
                        g.drawString(GetTowerName(i), x + width / 2 - textWidth / 2, (y + height / 2) + textHeight + 2);
                        g.drawString("Cost: " + GetTowerCost(i), x + width / 2 - textCost / 2, (y + height / 2) + textHeight * 2);
                    } else {
                        g.setColor(new Color(139, 0, 0));
                        g.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        textWidth = g.getFontMetrics().stringWidth("Not enought gold!");
                        textHeight = g.getFontMetrics().getHeight();
                        g.drawString("Not enought gold!", x + width / 2 - textWidth / 2, (y + height / 2) + textHeight + 2);
                    }
                }

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

