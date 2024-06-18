package Game.Enemies;

import Game.GameObject;

import java.awt.*;

import static Utils.Types.Enemies.*;

public class Enemy extends GameObject {


    private int id;
    private int enemyType;
    private int enemySize;
    private int health;
    protected int slowTick;
    private boolean isSlowed = false;
    private boolean isEnemyAlive = true;

    public Enemy(int position_X, int position_Y, int width, int height,  int id, int enemyType, int enemySize) {
        super(position_X, position_Y, width, height);
        this.id = id;
        this.enemyType = enemyType;
        this.enemySize = enemySize;
        setStartHealth();
    }

    public void move(float speed) {
        if(isSlowed()) {
            if (slowTick < 60 * 2) {
                slowTick++;
                speed *= 0.5f;
            }
        }

        this.position_X -= speed;
    }

    protected void setStartHealth() {
       health = GetStartHealth(enemyType, enemySize);
    }

    public int getMaxHealth() {
        return GetStartHealth(enemyType, enemySize);
    }

    public void getHit(int damage) {
        this.health -= damage;

        if(health <= 0) {
            health = 0;
            setEnemyAlive(false);
        }
    }

    public void getSlow() {
        slowTick = 0;
    }

    public boolean isSlowed() {
        return isSlowed;
    }

    public void setSlowed(boolean slowed) {
        isSlowed = slowed;
    }

    public  void paint(Graphics g, Image enemyImage) {
        g.drawImage(enemyImage, (int) this.position_X, (int) this.position_Y, null);

    }

    public int getId() {
        return id;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getEnemySize() {
        return enemySize;
    }

    public int getHealth() {
        return health;
    }

    public boolean isEnemyAlive() {
        return isEnemyAlive;
    }

    public void setEnemyAlive(boolean enemyAlive) {
        isEnemyAlive = enemyAlive;
    }
}
