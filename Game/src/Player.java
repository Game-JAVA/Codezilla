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
        if(!isDead())
            g.drawImage(img.getImage(), getX(), getY(), width, height, null);
    }

    public boolean receberDano(int dano, int x, int y) {
        if((y <= (getY() + height) && y >= getY()) && (x >= getX() && x <= (getX() + width))) {
            setVida(getVida() - dano);
            if(getVida() <= 0)
                return true;
        }
        return false;
    }

    @Override
    public void moveX(int inc) {
        if(!isDead())
            setX(getX() + getSpeedX()*inc);
    }

    @Override
    public void moveY(int inc) {
        if(!isDead())
            setY(getY() + getSpeedY()*inc);
    }

    public void animation() {
        setY(getY() - 1);
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

    public boolean isDead() {
        if(getVida() > 0)
            return false;
        else
            return true;
    }
}
