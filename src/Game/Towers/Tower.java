package Game.Towers;

import Game.GameObject;
import Game.GameWindow;

import java.awt.*;

import static Utils.Types.Towers.*;

public class Tower extends GameObject {
    private final GameWindow gameWindow;
    private final int id;
    private final int towerType;
    private int cooldownTick;
    private int damage, range, cooldown;
    private int upgradeAmount = 0;
    private int supportTick;
    private final int supportTime = 60 * 2;
    public TowerOptionBar towerDisplayBar;
    private boolean isTowerPressed;
    private boolean isSupported = false;

    public Tower(GameWindow gameWindow, int position_X, int position_Y, int width, int height, int id, int towerType) {
        super(position_X, position_Y, width, height);
        this.gameWindow = gameWindow;
        this.id = id;
        this.towerType = towerType;

        setDefaultDamage();
        setDefaultRange();
        setDefaultAttackCooldown();
        towerDisplayBar = new TowerOptionBar(gameWindow,this, towerType, (position_X - 175 / 2 + 32), position_Y - 100, 175, 80);
    }

    private void setDefaultDamage() {
        damage = GetStartDamage(towerType);
    }

    private void setDefaultRange() {
        range = GetRange(towerType);
    }

    private void setDefaultAttackCooldown() {
        cooldown = GetStartAttackCooldown(towerType);
    }

    public boolean isSupported() {
        return isSupported;
    }

    public void setSupported(boolean supported) {
        isSupported = supported;
    }

    public void getTowerSupport() {
        supportTick = 0;
    }

    public void paint(Graphics g, Image towerImage) {
        g.drawImage(towerImage, (int) this.position_X, (int) this.position_Y, null);

        if(isTowerPressed() && towerDisplayBar.isBarShown())
            towerDisplayBar.paint(g);
    }

    public boolean isCooldownOver() {
        return cooldownTick >= cooldown;
    }

    public boolean isSupportOver() {
        return supportTick >= supportTime;
    }

    public void resetCooldown() {
        cooldownTick = 0;
    }

    public void resetSupport() {
        supportTick = 0;
        setSupported(false);
    }

    public int getId() {
        return id;
    }

    public int getTowerType() {
        return towerType;
    }

    public boolean isTowerPressed() {
        return isTowerPressed;
    }

    public void setTowerPressed(boolean isTowerPressed) {
        this.isTowerPressed = isTowerPressed;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getUpgradeAmount() {
        return upgradeAmount;
    }

    public void upgradeTower(int towerType) {
        upgradeAmount++;

        switch (towerType) {
            case MAGE_TOWER: damage += 10; range += 10; break;
            case BOULDER_TOWER: damage += 5; range += 10; break;
            case ICE_TOWER: cooldown -= 10; range += 10; break;
            case SUPPORT_TOWER: cooldown -=20; break;
        }
    }

    public void showActionBar() {
        setTowerPressed(true);
        towerDisplayBar.setBarShown(true);
    }

    public void hideActionBar() {
        setTowerPressed(false);
        towerDisplayBar.setBarShown(false);

    }

    public void update() {
        cooldownTick++;

        if(isSupported()) {
            supportTick++;
            cooldown = GetStartAttackCooldown(towerType) - 20;
        } else
            cooldown = GetStartAttackCooldown(towerType);
    }

}
