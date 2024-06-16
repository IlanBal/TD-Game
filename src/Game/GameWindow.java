package Game;

import Game.Enemies.EnemyManager;
import Game.Towers.TowerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static Utils.Dimensions.*;

public class GameWindow extends JPanel implements Runnable, MouseListener, MouseMotionListener {
    private boolean game_running = true;
    private Thread gameThread;
    private GameCastle castle;
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private int playerGold = 10;
    private int waveNum = 1;


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
        castle = new GameCastle(CASTLE_X, CASTLE_Y);
        enemyManager = new EnemyManager(this);
        towerManager = new TowerManager(this);
        gameThread();

    }

    public void gameThread() {
        gameThread = new Thread(this);
        gameThread.start();
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
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Wave: " + waveNum, 20, 30);
        g.drawString("Gold:" + playerGold, 20, 50);

        this.castle.paint(g);
        this.towerManager.paint(g);
        this.enemyManager.paint(g);
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

    public GameCastle getCastle() {
        return castle;
    }

    private void updateGame() {
        if(castle.getCastleHealth() == 0) {
            JOptionPane.showMessageDialog((Component)null, "You're out of life, game will exit now", "Game Over", -1);
            System.exit(0);
        }

        enemyManager.moveEnemy();
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
