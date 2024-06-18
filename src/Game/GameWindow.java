package Game;

import Game.Enemies.Enemy;
import Game.Enemies.EnemyManager;
import Game.Projectiles.ProjectileManager;
import Game.Towers.Tower;
import Game.Towers.TowerManager;
import Game.Waves.WaveManager;

import java.text.DecimalFormat;
import java.util.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.TimerTask;

import static Utils.Dimensions.*;
import static Utils.Types.Enemies.GetEnemyGold;

public class GameWindow extends JPanel implements Runnable, MouseListener, MouseMotionListener {
    private boolean game_running = true;
    private Thread gameThread;
    private GameCastle castle;
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projectileManager;
    private WaveManager waveManager;
    private DecimalFormat formatter = new DecimalFormat("#.#");

    private int playerGold = 100;
    private int waveNum = 1;
    private int projectileCount = 0;


    public GameWindow() {
        JFrame gameFrame = new JFrame("Tower Defense Game");
        this.init();
        gameFrame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setContentPane(this);
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setLocationRelativeTo(null);
    }

    private void init() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        castle = new GameCastle(CASTLE_X, CASTLE_Y, 128, 128);
        enemyManager = new EnemyManager(this);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
        gameThread();

    }

    public void gameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double time = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        long lastUpdate = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;

        long currentTime;
        while(game_running) {

            currentTime = System.nanoTime();
            //Rendering
            if(currentTime - lastTime >= ns) {
                lastTime = currentTime;
                repaint();
            }

            //Updates
            if(currentTime - lastUpdate >= ns) {
                updateGame();
                lastUpdate = currentTime;
            }

            if(System.currentTimeMillis() - time >= 1000) {
                time = System.currentTimeMillis();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon backgroundImagePath = new ImageIcon(this.getClass().getResource("Images/background.png"));
        Image backgroundImage = backgroundImagePath.getImage();
        g.drawImage(backgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);

        ImageIcon floorImagePath = new ImageIcon(this.getClass().getResource("Images/floor.png"));
        Image floorImage = floorImagePath.getImage();
        g.drawImage(floorImage, 0, WINDOW_HEIGHT-floorImage.getHeight(this), floorImage.getWidth(this), floorImage.getHeight(this), this);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Dialog", Font.BOLD, 16));
        g.drawString("Wave: " + (waveManager.getWaveIndex() + 1), 20, 30);
        g.drawString("Enemies Left: " + enemyManager.getTotalEnemies(), 100, 30);
        g.drawString("HP: " + enemyManager.getEnemiesHealth(), 240, 30);
        g.drawString ("Gold: " + playerGold, 20, 50);

        if(waveManager.isWaveTimeStarted()) {
            float waveTimeLeft = waveManager.getTimeLeft();
            String timeLeft = formatter.format(waveTimeLeft);
            g.setFont(new Font("Dialog", Font.BOLD, 18));
            g.drawString("Time Left: " + timeLeft, 20, 70);
        }


        this.castle.paint(g);
        this.towerManager.paint(g);
        this.enemyManager.paint(g);
        this.projectileManager.paint(g);
    }



    private void updateGame() {
        waveManager.update();

        if (isAllEnemiesDead()) {
            waveManager.startWaveTimer();
            if(isWaveTimerOver()) {
                waveManager.incWaveIndex();
            }
        }

        if(isEnemySpawnTime()) {
            if(!waveManager.isWaveTimerOver()) {
                spawnEnemies();
            }
        }

        enemyManager.update();
        towerManager.update();
        projectileManager.update();


        if(castle.getCastleHealth() == 0) {
            JOptionPane.showMessageDialog(null, "You're out of life, game will exit now", "Game Over", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
    }

    private void spawnEnemies() {
        enemyManager.spawnEnemy(waveManager.getNextEnemyType(), waveManager.getNextEnemySize());
    }


    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimerOver();
    }

    private boolean isEnemySpawnTime() {
        if(waveManager.isEnemySpawnTime()) {
            return waveManager.isThereMoreEnemySpawn();
        }
        return false;
    }

    private boolean isAllEnemiesDead() {
        if(waveManager.isThereMoreEnemySpawn()) {
            return false;
        }

        for(Enemy enemy: enemyManager.getEnemyArray()) {
            if (enemy.isEnemyAlive()) {
                return false;
            }
        }
        return true;
    }

    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }

    public void shootEnemy(Tower tower, Enemy enemy) {
        projectileManager.addProjectile(tower, enemy);
    }

    public void throwRocks(Tower tower, Enemy enemy) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(projectileCount < 3) {
                    projectileManager.addProjectile(tower, enemy);
                    projectileCount++;
                } else timer.cancel();
            }
        }, 0, 300);
        projectileCount = 0;
    }

    public void shootIce(Tower tower, Enemy enemy) {
        projectileManager.addProjectile(tower, enemy);
        enemy.setSlowed(true);
        enemy.getSlow();
    }

    public void supportTowers(Tower tower) {
        for (int i=0; i<towerManager.getTowerArray().size(); i++) {
            Tower closeTower = towerManager.getTowerArray().get(i);

            if(closeTower.getId() != tower.getId())
                if(closeTower.position_X >= tower.position_X + tower.width/2 - tower.getRange()/2 || closeTower.position_X <= tower.position_X +tower.width - tower.getRange()/2) {
                    projectileManager.addTowerEffect(closeTower);
                }
        }
    }

    public void getEnemyGold(int enemyType, int enemySize) {
        addGold(GetEnemyGold(enemyType, enemySize));
    }

    public void addGold(int enemyGold) {
        this.playerGold += enemyGold;
    }

    public int getPlayerGold() {
        return playerGold;
    }

    public void setPlayerGold(int playerGold) {
        this.playerGold = playerGold;
    }

    public GameCastle getCastle() {
        return castle;
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        towerManager.mouseClicked(e.getX(), e.getY());

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        towerManager.mouseMoved(e.getX(), e.getY());
    }
}
