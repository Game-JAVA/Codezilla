import java.awt.*;
import java.time.Clock;

public abstract class Nave {
    private int x, y, speedX, speedY, vida, timeTiro, screenWidth, screenHeight;
    private Clock clock = Clock.systemDefaultZone();
    private long millisShoot, millisShoot2;
    private Sound explosion, hit, shoot;

    public Nave(int x, int y, int speedX, int speedY, int vida, int timeTiro, int screenWidth, int screenHeight, String urlExplosion, String urlShoot) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.vida = vida;
        this.timeTiro = timeTiro;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        explosion = new Sound(urlExplosion, false);
        shoot = new Sound(urlShoot, false);
        hit = new Sound("sounds/hitMedium.wav", false);

        millisShoot = clock.millis();
        millisShoot2 = millisShoot;
    }

    public boolean move() { return false; }

    public boolean atirar(int subTimeTiro, boolean enter) {
        if((getmillisShoot() - getmillisShoot2()) > (getTimeTiro() - subTimeTiro) && enter) {
            shoot.play();
            setmillisShoot();
            return true;
        }
        return false;
    }

    // Recebe o dano
    public boolean receberDano(int dano) {
        System.out.println("Acertou");
        setVida(getVida() - dano);
        if (getVida() <= 0) {
            explosion.play();
            return true;
        }
        hit.play();
        return false;
    }

    // Setter
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void setVida(int vida) { this.vida = vida; }

    public void setTimeTiro(int timeTiro) { this.timeTiro = timeTiro; }

    // Getters
    public int getX() { return x; }

    public int getY() { return y; }

    public int getSpeedX() { return speedX; }

    public int getSpeedY() { return speedY; }

    public int getVida() { return vida; }

    public int getTimeTiro() { return timeTiro; }

    public abstract int getWidth();

    public abstract int getHeight();

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
        millisShoot2 = millisShoot;
    }

    public int getScreenHeight() { return screenHeight; }

    public int getScreenWidth() { return screenWidth; }

    public abstract void draw(Graphics g);
}
