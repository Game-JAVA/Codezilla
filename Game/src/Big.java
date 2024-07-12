import java.awt.Graphics;
import java.util.Random;
import javax.swing.*;

public class Big extends Nave {
    private int width, height;
    private ImageIcon img;
    private static Random r = new Random();
    private boolean aux;

    // Construtor das naves
    public Big(int speedX, int speedY, int vida, double scale, int screenWidth, int screenHeight, String url) {
        super(200, screenHeight, speedX, speedY, vida, 2800, screenWidth, screenHeight, "/sounds/explosionSmall.wav", "/sounds/tiroSmall.wav");

        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);

        aux = r.nextBoolean();
        if(aux)
            setX((screenWidth - 200) - width);
    }

    // Métodos
    // Desenhar Inimigo
    public void draw(Graphics g) {
        g.drawImage(img.getImage(), getX(), getY(),
                width, height, null);
    }

    // Movimento
    @Override
    public boolean move() {
        if((getY() + getHeight()) >= 0) {
            setY(getY() - getSpeedY());
            return false;
        }
        return true;
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
