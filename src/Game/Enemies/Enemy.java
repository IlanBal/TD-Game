package Game.Enemies;

import Game.GameObject;

import java.awt.*;

import static Utils.Types.Enemies.*;

public class Enemy extends GameObject {


    private int ID;
    private int enemyType;
    private int enemySize;
    private int health;

    public Enemy(int position_X, int position_Y, int ID, int enemyType, int enemySize) {
        super(position_X, position_Y);
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemySize = enemySize;

    }

    public void move(int speed) {
        this.position_X -= speed;
    }

    public  void paint(Graphics g, Image enemyImage) {
        g.drawImage(enemyImage, this.position_X, this.position_Y, null);

    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(int enemyType) {
        this.enemyType = enemyType;
    }

    public int getEnemySize() {
        return enemySize;
    }

    public void setEnemySize(int enemySize) {
        this.enemySize = enemySize;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
