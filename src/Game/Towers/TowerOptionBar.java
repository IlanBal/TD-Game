package Game.Towers;

import Game.Bar;
import Game.CustomButton;
import Game.GameWindow;

import java.awt.*;

import static Utils.Types.Towers.*;

public class TowerOptionBar extends Bar {

    private GameWindow gameWindow;
    private Tower tower;
    private CustomButton upgradeButton, sellButton;
    private int towerType;
    private boolean isButtonPressed;
    private boolean isBarShown;
    private boolean isUpgradable = true;

    public TowerOptionBar(GameWindow gameWindow, Tower tower, int towerType, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.gameWindow = gameWindow;
        this.tower = tower;
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

            g.setColor(new Color(188, 108, 37));
            g.fillRect(x, y, width, height);

            g.setColor(Color.black);
            g.drawRect(x, y, width, height);

            drawTowerName(g);

            g.setColor(new Color(221, 161, 94));
            g.fillRect(upgradeButton.x, upgradeButton.y, upgradeButton.width, upgradeButton.height);
            g.fillRect(sellButton.x, sellButton.y, sellButton.width, sellButton.height);
            paintButton(g, upgradeButton);
            upgradeButton.paint(g, null);

            if(isUpgradable()) {
                if(upgradeButton.isMouseOver()) {
                    textWidth = g.getFontMetrics().stringWidth("Upgrade: " + GetUpgradeCost(towerType));
                    g.setColor(new Color(0, 71, 171));
                    g.drawString("Upgrade: " + GetUpgradeCost(towerType), x + width / 2 - textWidth / 2, y + height / 2 + upgradeButton.height);
                }
            } else {
                setButtonBlocked(true);
                upgradeButton.setButtonBlocked(true);
            }

            sellButton.paint(g, null);
            if(sellButton.isMouseOver()) {
                textWidth = g.getFontMetrics().stringWidth("Sell: " + (GetTowerCost(towerType)/2 + GetUpgradeCost(towerType)*tower.getUpgradeAmount()));
                g.setColor(new Color(139, 0, 0));
                g.drawString("Sell: " + (GetTowerCost(towerType)/2 + GetUpgradeCost(towerType)*tower.getUpgradeAmount()), x +width/2 - textWidth/2, y+height/2+ sellButton.height);
            }
        }
    }

    public void drawTowerName(Graphics g) {

        int textWidth, textHeight;

        switch (towerType) {
            case MAGE_TOWER:
                textWidth = g.getFontMetrics().stringWidth("MAGE TOWER");
                textHeight = g.getFontMetrics().getHeight();
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("MAGE TOWER", x + width / 2 - textWidth / 2, y + textHeight);
                break;
            case ICE_TOWER:
                textWidth = g.getFontMetrics().stringWidth("ICE TOWER");
                textHeight = g.getFontMetrics().getHeight();
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("ICE TOWER", x + width / 2 - textWidth / 2, y + textHeight);
                break;
            case BOULDER_TOWER:
                textWidth = g.getFontMetrics().stringWidth("BOULDER TOWER");
                textHeight = g.getFontMetrics().getHeight();
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("BOULDER TOWER", x + width / 2 - textWidth / 2, y + textHeight);
                break;
            case SUPPORT_TOWER:
                textWidth = g.getFontMetrics().stringWidth("SUPPORT TOWER");
                textHeight = g.getFontMetrics().getHeight();
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("SUPPORT TOWER", x + width / 2 - textWidth / 2, y + textHeight);
                break;
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

    public boolean isUpgradable() {
        return isUpgradable;
    }

    public void setUpgradable(boolean upgradable) {
        isUpgradable = upgradable;
    }

    public CustomButton getUpgradeButton() {
        return upgradeButton;
    }

    public CustomButton getSellButton() {
        return sellButton;
    }
}

