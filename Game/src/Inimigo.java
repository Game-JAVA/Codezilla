import java.awt.Graphics;
import java.time.Clock;
import javax.swing.*;

public class Inimigo extends Nave {
    private int width, height, centerY, centerX, raio;
    private double angle;
    private long millis, millis2;
    private ImageIcon img;
    Clock clock = Clock.systemDefaultZone();

    public Inimigo(int x, int y, int speedX, int speedY, int vida, double scale, int raio, String url) {
        super(x, y, speedX, speedY, vida);
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
        this.raio = raio;
        angle = 0;
        centerX = (int) (x - raio * Math.cos(angle));
        centerY = (int) (y - raio * Math.sin(angle));
        millis = clock.millis();
        millis2 = millis;
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), super.getX(), super.getY(),
                width, height, null);
    }

    public boolean receberDano(int dano, int x, int y) {
        if(y <= (super.getY() + height) && (x >= super.getX() && x <= (super.getX() + width))) {
            System.out.println("Acertou");
            super.setVida(super.getVida() - dano);
            if(super.getVida() <= 0)
                return true;
        }
        return false;
    }

    public void moviment1() {
            angle += 0.015; // Incrementa o ângulo

            int newX = (int) (centerX + raio * Math.cos(angle));
            int newY = (int) (centerY + raio * Math.sin(angle));

            // Atualiza a posição do objeto
            super.setX(newX);
            super.setY(newY);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getMillis() {
        millis = clock.millis();
        return millis;
    }

    public long getMillis2() {
        return millis2;
    }

    public void setMillis() {
        millis = clock.millis();
        millis2 = millis;
    }
}
