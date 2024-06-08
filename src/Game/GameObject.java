package Game;

import java.awt.*;

public class GameObject {
    public int position_X;
    public int position_Y;

    //Used for Game.Enemies.Enemy Object: We state the position, its health and its speed
    public GameObject(int position_X, int position_Y, int health, int speed) {
        this.position_X = position_X;
        this.position_Y = position_Y;

    }

    //Used for Player Towers: We state where we place the towers
    public GameObject(int position_X, int position_Y) {
        this.position_X = position_X;
        this.position_Y = position_Y;
    }

    public Rectangle getBounds(int width, int height) {
        return new Rectangle(position_X, position_Y, width, height);
    }
}
