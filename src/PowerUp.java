import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PowerUp {
    private int width, height, x, y, speed, screenWidth, screenHeight;
    private static Random r = new Random();
    private ImageIcon img;
    private boolean type;

    public PowerUp(int x, int y, int screenWidth, int screenHeight, double scale, boolean type, String url) {
        if(!type) {
            this.y = 0;
            this.x = r.nextInt(screenWidth - 200) + 100;
        } else {
            this.y = y;
            this.x = x + 30;
        }
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.type = type;
        speed = 4;

        img = new ImageIcon(this.getClass().getResource(url));

        width = (int) (img.getIconWidth() * scale);
        height = (int) (img.getIconHeight() * scale);
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), x, y,
                width, height, null);
    }

    public boolean move() {
        if(getY() <= screenHeight) {
            setY(getY() + speed);
            return true;
        }
        return false;
    }

    // Getters
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean getType() {
        return type;
    }

    //Setters
    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
