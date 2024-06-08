package Game;

import Game.Enemies.EnemyManager;
import Game.Towers.TowerManager;

import javax.swing.*;
import java.awt.*;

import static Utils.Dimensions.*;

public class GameWindow extends JPanel implements Runnable {
    private boolean game_running = true;
    private Thread gameThread;
    private JPanel gamePanel;
    private GameCastle castle;
    private TowerManager towerManager;
    private EnemyManager enemyManager;
    private int playerGold = 10;
    private int waveNum = 1;


    public GameWindow() {
        JFrame gameFrame = new JFrame("Tower Defense Game");
        this.init();
        gameFrame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //gameFrame.setResizable(false);
        gameFrame.setContentPane(this);
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setLocationRelativeTo(null);
    }

    private void init() {

        this.castle = new GameCastle(CASTLE_X, CASTLE_Y);
        this.enemyManager = new EnemyManager(this);
        this.towerManager = new TowerManager(this);
        this.gameThread();

    }

    public void gameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void checkCollision() {
/*        if(enemyManager.checkCollision(castle)) {
            enemyManager.
        }*/
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon backgroundImagePath = new ImageIcon(this.getClass().getResource("Images/background.png"));
        Image backgroundImage = backgroundImagePath.getImage();
        g.drawImage(backgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);

        ImageIcon floorImagePath = new ImageIcon(this.getClass().getResource("Images/floor.png"));
        Image floorImage = floorImagePath.getImage();
        g.drawImage(floorImage, 0, WINDOW_HEIGHT-floorImage.getHeight(this), floorImage.getWidth(this.gamePanel), floorImage.getHeight(this), this);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Wave: " + waveNum, 20, 30);
        g.drawString("Gold:" + playerGold, 20, 50);

        this.castle.paint(g);
        this.enemyManager.paint(g);
    }

    @Override
    public void run() {
        double time = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        long lastUpdate = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;

        int frames = 0;
        int updates = 0;

        long currentTime;
        while(game_running) {

            currentTime = System.nanoTime();
            //Rendering
            if(currentTime - lastTime >= ns) {
                lastTime = currentTime;
                repaint();
                frames++;
            }

            //Updates
            if(currentTime - lastUpdate >= ns) {
                updateGame();
                lastUpdate = currentTime;
                updates++;
            }

            if(System.currentTimeMillis() - time >= 1000) {
                System.out.println("FPS: " + frames + "|| UPS: " + updates);
                frames=0;
                updates=0;
                time = System.currentTimeMillis();
            }
        }
    }

    private void updateGame() {
        enemyManager.moveEnemy();
    }
}
