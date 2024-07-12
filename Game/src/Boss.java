import java.awt.Graphics;
import java.util.Random;
import javax.swing.*;

public class Boss extends Nave {
    private int width, height;
    private ImageIcon img;
    private static Random r = new Random();
    private boolean isDead = false;

    // Construtor das naves
    public Boss(int speedX, int speedY, int vida, double scale, int screenWidth, int screenHeight, String url) {
        super(0, 0, speedX, speedY, vida, 2200, screenWidth, screenHeight, "/sounds/explosionSmall.wav", "/sounds/tiroMedium.wav");

        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);

        setY(-height);
        setX((screenWidth/2) - (width/2));
    }

    // Métodos
    // Desenhar Inimigo
    public void draw(Graphics g) {
        if(!isDead)
            g.drawImage(img.getImage(), getX(), getY(), width, height, null);
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
