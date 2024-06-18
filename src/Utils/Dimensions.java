package Utils;

import java.awt.*;

public class Dimensions {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT;

    public static final int CASTLE_X;
    public static final int CASTLE_Y;
    public static final int CASTLE_HEALTH;

    public static final int ENEMY_X;
    public static final int ENEMY_Y;

    public static int GetDistance(int x1, int y1, int x2, int y2) {
        int xDiff = Math.abs(x1 - x2);
        int yDiff = Math.abs(y1 - y2);

        return (int) Math.hypot(xDiff,yDiff);
    }

    public Dimensions() {

    }

    static {
        WINDOW_WIDTH = (int) (screenSize.getWidth() / 2.0);
        WINDOW_HEIGHT = (int) (screenSize.getHeight() / 2.0);

        CASTLE_X = 20;
        CASTLE_Y = WINDOW_HEIGHT - 228;
        CASTLE_HEALTH = 5;

        ENEMY_X = WINDOW_WIDTH+100;
        ENEMY_Y = CASTLE_Y+96;
    }
}
