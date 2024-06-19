import java.awt.Graphics;
import javax.swing.*;

public class Player extends Nave {
    private int width, height;
    private ImageIcon img;

    public Player(int x, int y, int speedX, int speedY, int vida, double scale, String url) {
        super(x, y, speedX, speedY, vida);
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), super.getX(), super.getY(),
                width, height, null);
    }

    public boolean receberDano(int dano, int x, int y) {
        if(y <= (super.getY() + this.height) && (x >= super.getX() && x <= (super.getX() + width))) {
            System.out.println("Acertou");
            super.setVida(super.getVida() - dano);
            if(super.getVida() <= 0)
                return true;
        }
        return false;
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
