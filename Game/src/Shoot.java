import java.awt.Graphics;
import javax.swing.*;

public class Shoot {
    private int width, height, x, y, speed;
    private ImageIcon img;

    public Shoot(int x, int y, String url, double scale) {
        this.x = x;
        this.y = y;
        speed = 2;
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), x, y,
                width, height, null);
    }

    public boolean move(int screenHeight) {
        y -= speed;

        if (y + height >= screenHeight)
            return true;
        return false;
    }
}
