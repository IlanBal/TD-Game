package Game;

import java.awt.*;

public class CustomButton {
    public int x, y, width, height;
    private int id;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed, buttonBlocked;


    public CustomButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public CustomButton(String text, int x, int y, int width, int height, int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
    }


    public void paint(Graphics g, Image towerImage) {
        int textWidth = g.getFontMetrics().stringWidth(text);
        int textHeight = g.getFontMetrics().getHeight();

        if(buttonBlocked) {
            g.setColor(Color.darkGray);
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            g.drawRect(x, y, width, height);
        } else {
            if (mouseOver)
                g.setColor(Color.GRAY);
            else
                g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);

            g.setColor(Color.black);
            g.drawRect(x, y, width, height);
            if (mousePressed) {
                g.drawRect(x + 1, y + 1, width - 2, height - 2);
            }
        }

        if(towerImage != null)
            g.drawImage(towerImage, x, y, width, height, null);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(text, x + width/2 - textWidth/2 , y + height - textHeight/4);
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isButtonBlocked() {
        return buttonBlocked;
    }

    public void setButtonBlocked(boolean buttonBlocked) {
        this.buttonBlocked = buttonBlocked;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getId() {
        return id;
    }
}
