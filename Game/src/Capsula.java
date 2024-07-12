import java.awt.Graphics;
import java.time.Clock;
import java.util.function.BiPredicate;
import javax.swing.*;

public class Capsula {
    private int width, height, x, y, speed, dano, screenHeight, screenWidth, sinalX, sinalY, limitCap, centerX, centerY, subMillis;
    private double angle;
    private ImageIcon img;
    private byte ordem;
    private long millisShoot, millisShoot2;
    BiPredicate<Integer, Integer> comparisionPosition;
    private Clock clock = Clock.systemDefaultZone();

    // Construtor capsula
    public Capsula(int x, int y, int dano, int speed, String url, int screenHeight, int screenWidth, double scale, byte ordem) {
        this.dano = dano;
        this.speed = speed;
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.ordem = ordem;

        switch (ordem) {
            case 0:
                sinalY = -1;
                comparisionPosition = (a, b) -> a > b;
                limitCap = y - 250;
                angle = Math.toRadians(90);
                centerX = x;
                centerY = y;
                break;
            case 1:
                sinalX = 1;
                comparisionPosition = (a, b) -> a < b;
                limitCap = x + 250;
                angle = Math.toRadians(0);;
                centerX = x;
                centerY = y;
                break;
            case 2:
                sinalY = 1;
                comparisionPosition = (a, b) -> a < b;
                limitCap = y + 250;
                angle = Math.toRadians(270);
                centerX = x;
                centerY = y;
                break;
            case 3:
                sinalX = -1;
                comparisionPosition = (a, b) -> a > b;
                limitCap = x - 250;
                angle = Math.toRadians(180);
                centerX = x;
                centerY = y;
                break;
        }

        this.subMillis = ordem * 200;

        this.x = x;
        this.y = y;

        millisShoot = clock.millis();
        millisShoot2 = millisShoot;
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), x, y,
                width, height, null);
    }

    public boolean atirar() {
        if((getmillisShoot() - getmillisShoot2()) > 2000) {
            subMillis = 0;
            setmillisShoot();
            return true;
        }
        return false;
    }

    public boolean capsulaAnimation() {
        if(ordem % 2 == 0) {
            if (comparisionPosition.test(y, limitCap)) {
                y += speed * sinalY;
                return false;
            }
        }
        else {
            if (comparisionPosition.test(x, limitCap)) {
                x += speed * sinalX;
                return false;
            }
        }
        return true;
    }

    public void capsula() {
        angle += 0.01; // Incrementa o ângulo

        int newX = (int) (centerX + 250 * Math.cos(angle));
        int newY = (int) (centerY + 250 * Math.sin(angle));

        // Atualiza a posição do objeto
        x = newX;
        y = newY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getmillisShoot() {
        millisShoot = clock.millis();
        return millisShoot;
    }

    public long getmillisShoot2() {
        return millisShoot2;
    }

    // Reset millis
    public void setmillisShoot() {
        millisShoot = clock.millis();
        millisShoot2 = millisShoot + subMillis;
    }

    public int getDano() {
        return dano;
    }
}
