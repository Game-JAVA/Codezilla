import java.awt.Graphics;
import javax.swing.*;

public class Inimigo extends Nave {
    private int width, height;
    private ImageIcon img;

    public Inimigo(int x, int y, int speedX, int speedY, int vida, int dano, String url) {
        super(x, y, speedX, speedY, vida, dano);
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = img.getIconWidth();
        this.height = img.getIconHeight();
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), super.getX(), super.getY(),
                width, height, null);
    }

    public void scale(double scale) {
        width*=scale;
        height*=scale;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
