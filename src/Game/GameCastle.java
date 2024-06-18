package Game;

import javax.swing.*;
import java.awt.*;

import static Utils.Dimensions.CASTLE_HEALTH;

public class GameCastle extends GameObject {

    private ImageIcon castleImagePath = new ImageIcon(this.getClass().getResource("Images/castle.png"));
    private ImageIcon healthImagePath = new ImageIcon(this.getClass().getResource("Images/heart.png"));
    private Image castleImage;
    private Image healthImage;
    public int castleWidth, castleHeight;
    private int healthWidth, healthHeight;
    private int castleHealth;


    public GameCastle(int position_X, int position_Y, int width, int height) {
        super(position_X, position_Y, width, height);
        this.castleImage = this.castleImagePath.getImage();
        this.castleWidth = this.castleImagePath.getIconWidth();
        this.castleHeight = this.castleImagePath.getIconHeight();
        this.castleHealth = CASTLE_HEALTH;

        this.healthImage = this.healthImagePath.getImage();
        this.healthWidth = this.healthImagePath.getIconWidth();
        this.healthHeight = this.healthImagePath.getIconHeight();
    }

    public void paint(Graphics g) {
        g.drawImage(this.castleImage, (int) position_X, (int) position_Y, null);

        int distance=15;
        for(int i=0; i<this.castleHealth; distance += healthWidth + 5, i++) {
            g.drawImage(this.healthImage, (int) (this.position_X + distance), (int) (this.position_Y - 35), null);
        }
    }

    public void setCastleHealth(int health) {
        this.castleHealth = health;
    }

    public int getCastleHealth() {
        return this.castleHealth;
    }
}
