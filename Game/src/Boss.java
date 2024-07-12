import java.awt.Graphics;
import javax.swing.*;

public class Boss extends Nave {
    private int width, height, inc;
    private ImageIcon img;
    private boolean isDead = false;

    // Construtor das naves
    public Boss(int speedX, int speedY, int vida, double scale, int screenWidth, int screenHeight, String url) {
        super(0, 0, speedX, speedY, vida, 2800, screenWidth, screenHeight, "/sounds/explosionSmall.wav", "/sounds/tiroMedium.wav");

        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
        inc = 1;

        setY(-height);
        setX((screenWidth/2) - (width/2));
    }

    // Métodos
    // Desenhar Inimigo
    public void draw(Graphics g) {
        if(!isDead)
            g.drawImage(img.getImage(), getX(), getY(), width, height, null);
    }

    // Movimento
    @Override
    public boolean move() {
        if (getX() >= getScreenWidth() - getWidth() - 100) {
            inc = -1;
        }
        else if (getX() <= 100) {
            inc = 1;
        }

        setX(getX() + inc);
        return false;
    }

    // Animação de chegada
    public void animation() {
        setY(getY() + 1);
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Setters
    public void setDead(boolean dead) { isDead = dead; }
}
