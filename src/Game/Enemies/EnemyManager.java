package Game.Enemies;

import Game.GameWindow;
import Utils.Types;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static Utils.Dimensions.ENEMY_X;
import static Utils.Dimensions.ENEMY_Y;
import static Utils.Types.Enemies.*;

public class EnemyManager {

    private ArrayList<Enemy> enemyArray = new ArrayList<>();
    private GameWindow gameWindow;
    private ImageIcon enemyImagePath;
    private Image enemyImage;

    public EnemyManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        for(int i=0; i<3;i++)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    addEnemy(GRAY, MEDIUM);
                }
            }, 500 * i);
    }

    public void addEnemy(int enemyType, int enemySize) {
        int Y_OFFSET = (enemySize == SMALL) ? 8: (enemySize == LARGE) ? -16 : 0;
        enemyArray.add(new Enemy(ENEMY_X, ENEMY_Y + Y_OFFSET, 0, enemyType, enemySize));

        switch(enemyType) {
            case GRAY: enemyImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/enemy0.png"))); break;
            case GREEN: enemyImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/enemy1.png"))); break;
            case RED: enemyImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/enemy2.png"))); break;
            case BLUE: enemyImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/enemy3.png"))); break;
        }

        if ((enemySize == SMALL)) {
            resizeEnemyImage(enemyImagePath, -8);
        } else if ((enemySize == LARGE)) {
            resizeEnemyImage(enemyImagePath, 16);
        } else
            resizeEnemyImage(enemyImagePath, 0);
    }

    public void removeEnemy(Enemy enemy) {
        enemyArray.remove(enemy);
    }

    public void moveEnemy() {
        for(Enemy enemy: enemyArray) {
            enemy.move(GetSpeed(enemy.getEnemySize()));
        }

        checkCollision();
    }

    public void checkCollision() {
        for (int i = 0; i < this.enemyArray.size(); i++) {
            Enemy enemy = enemyArray.get(i);
            if (enemy.position_X == gameWindow.getCastle().position_X+gameWindow.getCastle().castleWidth) {
                gameWindow.getCastle().setCastleHealth(gameWindow.getCastle().getCastleHealth()-1);
                removeEnemy(enemy);
            }
        }
    }


    private void resizeEnemyImage(ImageIcon originalIcon, int scale) {
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();

        int newWidth = originalWidth + scale;
        int newHeight = originalHeight + scale;

        enemyImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }



    public void paint(Graphics g) {
        for(Enemy enemy: enemyArray) {
            enemy.paint(g, enemyImage);
        }
    }
}
