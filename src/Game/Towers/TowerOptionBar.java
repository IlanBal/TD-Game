package Game.Towers;

import Game.Bar;
import Game.CustomButton;
import Game.GameWindow;

import java.awt.*;

import static Utils.Types.Towers.*;

public class TowerOptionBar extends Bar {

    private GameWindow gameWindow;
    private CustomButton upgradeButton, sellButton;
    private int towerType;
    private boolean isButtonPressed;
    private boolean isBarShown;

    public TowerOptionBar(GameWindow gameWindow,int towerType, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.gameWindow = gameWindow;
        this.towerType = towerType;
        initButtons();
    }

    private void initButtons() {

        upgradeButton = new CustomButton("Update", x + 18, (y + height/2) - 16, 64, 32);
        sellButton = new CustomButton("Sell", x + 18 + 64 + 11, (y + height/2) - 16, 64, 32);
    }

    public void paint(Graphics g) {
        if (isBarShown() && !isButtonPressed()) {
            int textWidth;
            int textHeight;

            g.setColor(new Color(188, 108, 37));
            g.fillRect(x, y, width, height);

            g.setColor(Color.black);
            g.drawRect(x, y, width, height);

            switch(towerType) {
                case MAGE_TOWER:
                    textWidth = g.getFontMetrics().stringWidth("MAGE TOWER");
                    textHeight = g.getFontMetrics().getHeight();
                    g.setFont(new Font("Arial", Font.BOLD, 16));
                    g.drawString("MAGE TOWER", x+width/2-textWidth/2, y + textHeight);
                    break;
                case ICE_TOWER:
                    textWidth = g.getFontMetrics().stringWidth("ICE TOWER");
                    textHeight = g.getFontMetrics().getHeight();
                    g.setFont(new Font("Arial", Font.BOLD, 16));
                    g.drawString("ICE TOWER", x+width/2-textWidth/2, y + textHeight);
                    break;
                case BOULDER_TOWER:
                    textWidth = g.getFontMetrics().stringWidth("BOULDER TOWER");
                    textHeight = g.getFontMetrics().getHeight();
                    g.setFont(new Font("Arial", Font.BOLD, 16));
                    g.drawString("BOULDER TOWER", x+width/2-textWidth/2, y + textHeight);
                    break;
                case SUPPORT_TOWER:
                    textWidth = g.getFontMetrics().stringWidth("SUPPORT TOWER");
                    textHeight = g.getFontMetrics().getHeight();
                    g.setFont(new Font("Arial", Font.BOLD, 16));
                    g.drawString("SUPPORT TOWER", x+width/2-textWidth/2, y + textHeight);
                    break;
            }

            g.setColor(new Color(221, 161, 94));
            g.fillRect(upgradeButton.x, upgradeButton.y, upgradeButton.width, upgradeButton.height);
            g.fillRect(sellButton.x, sellButton.y, sellButton.width, sellButton.height);

            upgradeButton.paint(g, null);
            paintButton(g, upgradeButton);
            sellButton.paint(g, null);
            paintButton(g, sellButton);
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

    public CustomButton getUpgradeButton() {
        return upgradeButton;
    }

    public CustomButton getSellButton() {
        return sellButton;
    }
}

