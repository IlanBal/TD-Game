package Game;

import Game.Towers.TowerAddButton;

import javax.swing.*;
import java.awt.*;

public class Bar {

    public int x,y, width, height;
    private boolean isButtonBlocked = false;

    public Bar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paintButton(Graphics g, CustomButton button) {
        if (isButtonBlocked()) {
            g.setColor(Color.darkGray);
            g.fillRect(button.x, button.y, button.width, button.height);
            g.setColor(Color.black);
            g.drawRect(button.x, button.y, button.width,button.height);
        } else {
            if (button.isMouseOver())
                g.setColor(Color.WHITE);
            else
                g.setColor(Color.BLACK);

            g.drawRect(button.x, button.y, button.width, button.height);

            if (button.isMousePressed()) {
                g.drawRect(button.x + 1, button.y + 1, button.width - 2, button.height - 2);
            }
        }
    }

    public boolean isButtonBlocked() {
        return isButtonBlocked;
    }

    public void setButtonBlocked(boolean buttonBlocked) {
        isButtonBlocked = buttonBlocked;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
