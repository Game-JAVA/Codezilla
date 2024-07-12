import java.awt.Graphics;
import java.util.Random;
import javax.swing.*;

public class Medium extends Nave {
    private int width, height;
    private ImageIcon img;
    private static Random r = new Random();
    private boolean aux;

    // Construtor das naves
    public Medium(int speedX, int speedY, int vida, double scale, int screenWidth, int screenHeight, String url) {
        super(0, 0, speedX, speedY, vida, 2200, screenWidth, screenHeight, "/sounds/explosionSmall.wav", "/sounds/tiroMedium.wav");

        aux = r.nextBoolean();
        if(aux)
            setX(screenWidth);

        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
    }

    // Métodos
    // Desenhar Inimigo
    public void draw(Graphics g) {
        g.drawImage(img.getImage(), getX(), getY(),
                width, height, null);
    }

    // Movimento triangular (nave média)
    @Override
    public boolean move() {
        // Calcula a diferença entre o destino e o tiro (cateto)
        int cateto1 = (getScreenWidth()/2) - getX();
        int cateto2 = 300 - getY();

        // Verifica se o objeto já está próximo o suficiente do destino
        if (Math.abs(cateto1) <= getSpeedX() && Math.abs(cateto2) <= getX()) {
            setX(getScreenWidth()/2);
            setY(300);
        } else {
            // Hipotenusa
            double hipotenusa = Math.sqrt(cateto1 * cateto1 + cateto2 * cateto2);

            // Normaliza o vetor de direção
            double directionX = cateto1 / hipotenusa;
            double directionY = cateto2 / hipotenusa;

            // Ponto a ser alcaçado
            double moveX = directionX * getSpeedX();
            double moveY = directionY * getSpeedY();

            setX((int) (getX() + moveX));
            setY((int) (getY() + moveY));
        }
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
