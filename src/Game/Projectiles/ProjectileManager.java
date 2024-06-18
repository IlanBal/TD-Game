package Game.Projectiles;

import Game.Enemies.Enemy;
import Game.GameWindow;
import Game.Towers.Tower;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utils.Types.Projectiles.*;

public class ProjectileManager {

    private final GameWindow gameWindow;
    private final ArrayList<Projectile> projectileArray = new ArrayList<>();
    private ImageIcon projectileImagePath;
    private Image[] projectileImage;
    private int projectileId = 0;

    public ProjectileManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        loadProjectileImages();

    }

    public void addProjectile(Tower tower, Enemy enemy) {
        int projectileType = GetProjectileType(tower);

        float towerCenterX = tower.position_X + tower.width / 2;
        float towerCenterY = tower.position_Y + tower.height / 2;

        float enemyCenterX = enemy.position_X + enemy.width / 2;
        float enemyCenterY = enemy.position_Y + enemy.height / 2;

        double dx = enemyCenterX - towerCenterX;
        double dy = enemyCenterY - towerCenterY;

        double distance = Math.sqrt(dx * dx + dy * dy);
        dx /= distance;
        dy /= distance;

        double xSpeed = dx * GetSpeed(projectileType);
        double ySpeed = dy * GetSpeed(projectileType);

        projectileArray.add(new Projectile(towerCenterX,towerCenterY, 8, 8, xSpeed, ySpeed, tower.getDamage(), projectileId++,  projectileType));
    }

    public void addTowerEffect(Tower affectedTower) {
        Projectile tempProjectile = new Projectile(affectedTower.position_X+affectedTower.width/2 - 8, affectedTower.position_Y - 24, 16,16, 0,0, 0, projectileId++, SUPPORT);

        affectedTower.setSupported(true);
        affectedTower.getTowerSupport();

        projectileArray.add(tempProjectile);
    }

    private void loadProjectileImages() {
        projectileImage = new Image[4];
        for (int i = 0; i < projectileImage.length; i++) {
            projectileImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/tower_attack" + i + ".png")));
            projectileImage[i] = projectileImagePath.getImage();
        }
    }

    public void paint(Graphics g) {
        for(Projectile projectile: projectileArray)
            if(projectile.isProjectileActive()) {
                g.drawImage(projectileImage[projectile.getProjectileType()], (int) projectile.position_X, (int) projectile.position_Y, null);
            }
    }

    public void update() {

        for(int i = 0; i < projectileArray.size(); i++) {
            Projectile projectile = projectileArray.get(i);
            if(projectile.isProjectileActive())
                projectile.move();
            else projectileArray.remove(projectile);

            checkCollision(projectile);
        }
    }

    private void checkCollision(Projectile projectile) {
        for(int i=0; i < gameWindow.getEnemyManager().getEnemyArray().size(); i++) {
            Enemy enemy = gameWindow.getEnemyManager().getEnemyArray().get(i);
            if (projectile.isProjectileActive()) {
                if (enemy.getBounds().contains(projectile.position_X, projectile.position_Y)) {
                    enemy.getHit(projectile.getDamage());
                    if(!enemy.isEnemyAlive()) {
                        gameWindow.getEnemyGold(enemy.getEnemyType(), enemy.getEnemySize());
                        gameWindow.getEnemyManager().getEnemyArray().remove(enemy);
                    }

                    projectile.setProjectileActive(false);
                }
            }
        }
    }

    public ArrayList<Projectile> getProjectileArray() {
        return projectileArray;
    }
}

