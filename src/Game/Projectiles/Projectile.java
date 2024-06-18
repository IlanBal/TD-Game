package Game.Projectiles;

import Game.GameObject;

public class Projectile extends GameObject {

    private int id, projectileType, damage;
    private double xSpeed, ySpeed;
    private boolean isProjectileActive = true;


    public Projectile(float position_X, float position_Y, int width, int height, double xSpeed, double ySpeed, int damage,  int id, int projectileType) {
        super(position_X, position_Y, width, height);
        this.id = id;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.damage = damage;
        this.projectileType = projectileType;
    }

    public void move() {
        position_X += (float) xSpeed;
        position_Y += (float) ySpeed;
    }

    public int getId() {
        return id;
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public boolean isProjectileActive() {
        return isProjectileActive;
    }

    public void setProjectileActive(boolean projectileActive) {
        isProjectileActive = projectileActive;
    }
}
