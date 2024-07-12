import java.awt.Graphics;
import javax.swing.*;

public class Shoot {
    private int width, height, x, y, speed, dano, cateto2, cateto1, screenHeight, screenWidth;
    private ImageIcon img;
    private boolean aux = false;

    // Construtor tiro player
    public Shoot(int x, int y, int dano, int speed, String url, int screenHeight, int screenWidth, double scale, byte aux, int naveWidth) {
        this.dano = dano;
        this.speed = speed;
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Diferença da distancia dos tiros da nave player
        switch (aux) {
            case 0:
                this.x = x + 7;
                this.y = y;
                break;
            case 1:
                this.x = (x + naveWidth) - this.width - 10;
                this.y = y;
                break;
            case 2:
                this.x = (x + naveWidth) - 5;
                this.y = y + 8;
                break;
            case 3:
                this.x = x - 5;
                this.y = y + 8;
                break;
        }
    }

    // Construtor tiro inimigo
    public Shoot(int x, int y, int dano, int speed, String url, int screenHeight, int screenWidth, double scale) {
        this.dano = dano;
        this.speed = speed;
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), x, y,
                width, height, null);
    }

    public boolean move() {
        y -= speed;

        if (y + height <= 0)
            return true;
        return false;
    }

    public boolean move2(int destX, int destY) {
        // Calcula a diferença entre o destino e o tiro (cateto)
        if(aux == false) {
            cateto1 = destX - x;
            cateto2 = destY - y;
        }

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
            aux = true;
        }
        if ((y >= screenHeight || (y + height) <= 0) || (x >= screenWidth || (x + width) <= 0))
            return true;
        return false;
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
