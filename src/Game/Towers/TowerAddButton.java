package Game.Towers;

import Game.CustomButton;
import Game.GameWindow;

import java.awt.*;

public class TowerAddButton extends CustomButton {
    private GameWindow gameWindow;
    public TowerAddBar towerSelectionBar;
    public int id;

    public TowerAddButton(GameWindow gameWindow, String text, int x, int y, int width, int height) {
        super(text, x, y, width, height);
        this.gameWindow = gameWindow;
    }

    public TowerAddButton(GameWindow gameWindow, String text, int x, int y, int width, int height, int id) {
        super(text, x, y, width, height);
        this.gameWindow = gameWindow;
        this.id = id;
    }

    public void paintBar(Graphics g) {
        if(isMousePressed() && towerSelectionBar.isBarShown())
            towerSelectionBar.paint(g);
    }

    public TowerAddButton(String text, int x, int y, int width, int height, int id) {
        super(text, x, y, width, height, id);
        hideActionBar();
    }


    public void showActionBar() {
        setMousePressed(true);
        towerSelectionBar = new TowerAddBar(gameWindow, x - 175/2 +10 , y - 100, 175, 90);
        towerSelectionBar.setBarShown(true);
    }

    public void hideActionBar() {
        setMousePressed(false);
        if(towerSelectionBar != null)
            towerSelectionBar.setBarShown(false);
    }
}
