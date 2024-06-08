package Game.Towers;


import Game.GameWindow;

import javax.swing.*;
import java.awt.*;

import static Utils.Types.Towers.MAGE_TOWER;

public class TowerManager {

    private GameWindow gameWindow;
    private int id;
    private int type;
    private PlayerTower tower;
    private Image[] towerImage = new Image[4];

    public TowerManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        loadTowerImages();
    }

    private void loadTowerImages() {
        for(int i=0; i<towerImage.length; i++) {
            ImageIcon towerImagePath = new ImageIcon(gameWindow.getClass().getResource("Images/tower"+i+".png"));
            towerImage[i] = towerImagePath.getImage();
        }



    }

    public void add() {
    }

    public void paint(Graphics g) {
        g.drawImage(towerImage[MAGE_TOWER], tower.position_X, tower.position_Y, null);
    }
}
