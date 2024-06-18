package Game.Enemies;

import Game.GameWindow;
import Game.Waves.Wave;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utils.Dimensions.ENEMY_X;
import static Utils.Dimensions.ENEMY_Y;
import static Utils.Types.Enemies.*;

public class EnemyManager {

    private ArrayList<Enemy> enemyArray = new ArrayList<>();
    private GameWindow gameWindow;
    private ImageIcon enemyImagePath;
    private Image[] enemyImage;
    private int enemyNum = 0;

    public EnemyManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        enemyImage = new Image[4];
    }


    public void addEnemy(int enemyType, int enemySize) {
        int Y_OFFSET = (enemySize == SMALL) ? 8: (enemySize == LARGE) ? -16 : 0;
        enemyArray.add(new Enemy(ENEMY_X, (ENEMY_Y + Y_OFFSET), GetEnemySize(enemySize), GetEnemySize(enemySize), enemyNum++, enemyType, enemySize));

        for (int i = 0; i < enemyImage.length; i++) {
            if(i == enemyType) {
                enemyImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/enemy" + i + ".png")));
                if ((enemySize == SMALL)) {
                    enemyImage[enemyType] = resizeEnemyImage(enemyImagePath, -8);
                } else if ((enemySize == LARGE)) {
                    enemyImage[enemyType] = resizeEnemyImage(enemyImagePath, 16);
                } else
                    enemyImage[enemyType] = resizeEnemyImage(enemyImagePath, 0);
            }
        }

    }

    public void removeEnemy(Enemy enemy) {
        enemyArray.remove(enemy);
    }

    public void moveEnemy() {
        for(int i=0; i<enemyArray.size(); i++) {
            Enemy enemy = enemyArray.get(i);
            if (enemy.isEnemyAlive()) {
                enemy.move(GetSpeed(enemy.getEnemySize()));
                checkCollision();
            }
        }
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

    private Image resizeEnemyImage(ImageIcon originalIcon, int scale) {
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();

        int newWidth = originalWidth + scale;
        int newHeight = originalHeight + scale;

        return originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    public ArrayList<Enemy> getEnemyArray() {
        return enemyArray;
    }

    public void paint(Graphics g) {
        for(int i=0; i< enemyArray.size(); i++) {
            if(enemyArray.get(i).isEnemyAlive()) {
                enemyArray.get(i).paint(g, enemyImage[enemyArray.get(i).getEnemyType()]);
                paintEnemyHealth(g, enemyArray.get(i));
            }
        }
    }

    private void paintEnemyHealth(Graphics g, Enemy enemy) {
        double healthRatio = (double) enemy.getHealth() / enemy.getMaxHealth();
        int healthBarWidth = (int) (healthRatio * enemy.width);

        g.setColor(Color.RED);
        g.fillRect((int) enemy.position_X, (int) (enemy.position_Y - 8), healthBarWidth, 3);

        g.setColor(Color.BLACK);
        g.drawRect((int) enemy.position_X, (int) (enemy.position_Y - 8), enemy.width, 3);

    }

    public void update() {
        moveEnemy();
    }

    public void spawnEnemy(int enemyType, int enemySize) {
        addEnemy(enemyType, enemySize);
    }

    public int getTotalEnemies() {
        int totalEnemies = 0;
        for(Enemy enemy: enemyArray) {
            if (enemy.isEnemyAlive()) {
                totalEnemies++;
            }
        }
        return totalEnemies;
    }

    public int getEnemiesHealth() {
        int totalHealth = 0;
        for(Enemy enemy: enemyArray) {
            if(enemy.isEnemyAlive())
                totalHealth += enemy.getHealth();
        }

        return Math.max(totalHealth, 0);

    }
}


