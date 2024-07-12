import java.awt.Graphics;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;

public class Player extends Nave {
    private int width, height, timeTiro = 300;
    private ImageIcon img;
    private Sound powerUp;

    public Player(int x, int y, int speedX, int speedY, int vida, double scale, String url) {
        super(x, y, speedX, speedY, vida, 300,"/sounds/explosionPlayer.wav", "/sounds/tiro.wav");
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);

        powerUp = new Sound("/sounds/powerUp.wav", false);
    }

    public void draw(Graphics g) {
        if(!isDead())
            g.drawImage(img.getImage(), getX(), getY(), width, height, null);
    }

    public void moveX(int inc) {
        if(!isDead())
            setX(getX() + getSpeedX()*inc);
    }

    public void moveY(int inc) {
        if(!isDead())
            setY(getY() + getSpeedY()*inc);
    }

    public boolean pegarPowerUp(int x, int y, int width, int height) {
        if(getX() < x + width && (getX() + this.width) > x && getY() < y + height && (getY() + this.height) > y) {
            powerUp.play();
            return true;
        }
        return false;
    }

    public void armadura() {
        setVida(20);
    }

    public void animation() {
        setY(getY() - 1);
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //Setters
    public ImageIcon getImg() {
        return img;
    }

    public void setImg(String url, double scale) {
        this.img = new ImageIcon(this.getClass().getResource(url));
        width = (int) (img.getIconWidth() * scale);
        height = (int) (img.getIconHeight() * scale);
    }

    public boolean isDead() {
        return getVida() <= 0;
    }
}
