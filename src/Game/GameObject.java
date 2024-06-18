package Game;

import java.awt.*;

public class GameObject {
    public float position_X;
    public float position_Y;
    public int width, height;

    public GameObject(float position_X, float position_Y, int width, int height) {
        this.position_X = position_X;
        this.position_Y = position_Y;
        this.width = width;
        this.height = height;
    }



    public Rectangle getBounds() {
        return new Rectangle((int) position_X,(int) position_Y, width, height);
    }
}
