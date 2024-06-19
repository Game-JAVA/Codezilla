import java.awt.Graphics;
import javax.swing.*;

public class Shoot {
    private int width, height, x, y, speed, dano;
    private ImageIcon img;

    public Shoot(int x, int y, int dano, String url, double scale) {
        this.x = x;
        this.y = y;
        this.dano = dano;
        speed = 6;
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), x, y,
                width, height, null);
    }

    public boolean move(int screenHeight) {
        y -= speed;

        if (y + height >= screenHeight)
            return true;
        return false;
    }

    public void move2(int destX, int destY) {
        // Calcula a diferença entre o destino e o tiro (cateto)
        int cateto1 = destX - x;
        int cateto2 = destY - y;

        // Verifica se o objeto já está próximo o suficiente do destino
        if (Math.abs(cateto1) <= speed && Math.abs(cateto2) <= speed) {
            x = destX;
            y = destY;
        } else {
            // Hipotenusa
            double hipotenusa = Math.sqrt(cateto1 * cateto1 + cateto2 * cateto2);

            // Normaliza o vetor de direção
            double directionX = cateto1 / hipotenusa;
            double directionY = cateto2 / hipotenusa;

            // Ponto a ser alcaçado
            double moveX = directionX * speed;
            double moveY = directionY * speed;

            x += (int) moveX;
            y += (int) moveY;
        }
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

    public int getDano() {
        return dano;
    }
}
