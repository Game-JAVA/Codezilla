import java.awt.Graphics;
import java.time.Clock;
import java.util.Random;
import javax.swing.*;

public class Small extends Nave {
    private int width, height, rand500;
    private long millisHorda, millisHorda2;
    private ImageIcon img;
    private Clock clock = Clock.systemDefaultZone();
    private static Random r = new Random();
    private boolean aux, aux2;

    // Construtor das naves
    public Small(int speedX, int speedY, int vida, double scale, int screenWidth, int screenHeight, String url) {
        super(0, r.nextInt((screenHeight / 3) * 2), speedX, speedY, vida, 2200, screenWidth, screenHeight, "/sounds/explosionSmall.wav", "/sounds/tiroSmall.wav");

        aux = r.nextBoolean();
        if(aux)
            setX(screenWidth);

        rand500 = r.nextInt(100);

        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);

        millisHorda = clock.millis();
        millisHorda2 = millisHorda;
    }

    // Métodos
    // Desenhar Inimigo
    public void draw(Graphics g) {
        g.drawImage(img.getImage(), getX(), getY(),
                width, height, null);
    }

    // Movimento reto (nave pequena)
    @Override
    public boolean move() {
        if(getX() < 200 || getX() > (getScreenWidth() - 200) || (getX() % (500 + rand500) != 0) || !aux2) {
            if(!aux) {
                setX(getX() + getSpeedX());
                // setY(getY() + randY);
            }
            else {
                setX(getX() - getSpeedX());
                // setY(getY() + randY);
            }
            aux2 = r.nextBoolean();
            millisHorda = clock.millis();
            millisHorda2 = millisHorda;
        } else {
            millisHorda = clock.millis();

            if((millisHorda - millisHorda2) > 3000)
                aux2 = false;
        }

        if (getX() >= getScreenWidth() || (getX() + width) <= 0)
            return true;
        return false;
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
