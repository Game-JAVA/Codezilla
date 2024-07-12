import java.awt.Graphics;
import javax.swing.*;
import java.util.function.BiPredicate;

public class Verme extends Nave {
    private int width, height, centerY, centerX, raio, sinalAngle, sinalPI;
    private double angle;
    private ImageIcon img;
    BiPredicate<Double, Double> comparisionRadian;

    // Construtor da nave de horda
    public Verme(int vida, double scale, int screenWidth, int screenHeight, int rand, String url) {
        super(screenWidth, 0, 0, 0, vida, 3000, screenWidth, screenHeight, "/sounds/explosionSmall.wav", "/sounds/tiroVerme.wav");

        // Centro do movimento
        centerX = screenWidth / 2;
        raio = screenWidth / 2;

        switch (rand) {
            case 0: // Se começar no canto superior direito
                angle = 0;
                sinalAngle = 1;
                sinalPI = 1;
                comparisionRadian = (angle, radian) -> angle < radian;
                centerY = 0;
                break;
            case 1: // Se começar no canto superior esquerdo
                setX(0);
                angle = Math.PI;
                sinalAngle = -1;
                sinalPI = -1; // Usar apenas -1 PI para semicircular
                comparisionRadian = (angle, radian) -> angle > radian;
                centerY = 0;
                break;
            case 2: // Se começar no canto inferior direito
                setY(screenHeight);
                angle = 0;
                sinalAngle = -1;
                sinalPI = -1;
                comparisionRadian = (angle, radian) -> angle > radian;
                centerY = screenHeight;
                break;
            case 3: // Se começar no canto inferior esquerdo
                setX(0);
                setY(screenHeight);
                angle = Math.PI;
                sinalAngle = 1;
                sinalPI = 2; // Usar apenas 1 PI para semicircular
                comparisionRadian = (angle, radian) -> angle < radian;
                centerY = screenHeight;
                break;
        }

        img = new ImageIcon(this.getClass().getResource(url));
        width = (int) (img.getIconWidth() * scale);
        height = (int) (img.getIconHeight() * scale);
    }

    // Métodos
    // Desenhar Inimigo
    public void draw(Graphics g) {
        g.drawImage(img.getImage(), getX(), getY(),
                width, height, null);
    }

    // Movimento semicircular
    @Override
    public boolean move() {
        if (comparisionRadian.test(angle, Math.PI * sinalPI)) {
            angle += 0.01 * sinalAngle; // Incrementa o ângulo

            int newX = (int) (centerX + raio * Math.cos(angle));
            int newY = (int) (centerY + raio * Math.sin(angle));

            // Atualiza a posição do objeto
            setX(newX);
            setY(newY);
            return false;
        } else
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
