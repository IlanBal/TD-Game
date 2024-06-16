package Game.Towers;

import Game.GameObject;
import Game.GameWindow;

import java.awt.*;

public class Tower extends GameObject {
    private GameWindow gameWindow;
    private int id, towerType;
    public TowerOptionBar towerDisplayBar;
    private boolean isTowerPressed;

    public Tower(GameWindow gameWindow, int position_X, int position_Y, int id, int towerType) {
        super(position_X, position_Y);
        this.gameWindow = gameWindow;
        this.id = id;
        this.towerType = towerType;
        towerDisplayBar = new TowerOptionBar(gameWindow, towerType, (position_X - 175 / 2 + 32), position_Y - 100, 175, 80);
    }

    public void paint(Graphics g, Image towerImage) {
        g.drawImage(towerImage, this.position_X, this.position_Y, null);

        if(isTowerPressed() && towerDisplayBar.isBarShown())
            towerDisplayBar.paint(g);
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

    public void showActionBar() {
        setTowerPressed(true);
        towerDisplayBar.setBarShown(true);
    }

    public void hideActionBar() {
        setTowerPressed(false);
        towerDisplayBar.setBarShown(false);

    }

}
