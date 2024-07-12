import java.awt.Graphics;
import javax.swing.*;

public class Player extends Nave {
    private int width, height, timeTiro = 300;
    private ImageIcon img;
    private Sound powerUp;
    private boolean shootPlus = false;

    public Player(int x, int y, int speedX, int speedY, int vida, double scale, int screenWidth, int screenHeight, String url) {
        super(x, y, speedX, speedY, vida, 300, screenWidth, screenHeight, "/sounds/explosionPlayer.wav", "/sounds/tiro.wav");
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);

        powerUp = new Sound("/sounds/powerUp.wav", false);
    }

    public void draw(Graphics g) {
        if(!isDead())
            g.drawImage(img.getImage(), getX(), getY(), width, height, null);
    }

    public void moveX(int inc) {
        if (!isDead()) {
            int newX = getX() + getSpeedX() * inc;
            if (newX < 0) {
                setX(0);
            } else if (newX > getScreenWidth() - getWidth()) {
                setX(getScreenWidth() - getWidth());
            } else {
                setX(newX);
            }
        }
    }

    public void moveY(int inc) {
        if (!isDead()) {
            int newY = getY() + getSpeedY() * inc;
            if (newY < 0) {
                setY(0);
            } else if (newY > getScreenHeight() - getHeight() - 50) {
                setY(getScreenHeight() - getHeight() - 50);
            } else {
                setY(newY);
            }
        }
    }

    public boolean pegarPowerUp(int x, int y, int width, int height, boolean type) {
        if(getX() < x + width && (getX() + this.width) > x && getY() < y + height && (getY() + this.height) > y) {
            powerUp.play();
            if (!type) {
                setImg("images/nave_powerUp.gif", 0.7);
                armadura();
            } else {
                shootPlus = true;
            }
            return true;
        }
        return false;
    }

    public void armadura() {
        setVida(20);
    }

    public void animation() {
        setY(getY() - 1);
    }

    public boolean animation2() {
        // Calcula a diferença entre o destino e o tiro (cateto)
        int cateto1 = (getScreenWidth()/2) - getX();
        int cateto2 = 800 - getY();

        // Verifica se o objeto já está próximo o suficiente do destino
        if (Math.abs(cateto1) <= getSpeedX() && Math.abs(cateto2) <= getX()) {
            setX(getScreenWidth()/2);
            setY(800);
            return true;
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
            return false;
        }
    }

    public void animationFinal() {
        setY(getY() - 3);
    }

    // Getters
    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public ImageIcon getImg() { return img; }

    public boolean getShootPlus() { return shootPlus; }

    //Setters
    public void setImg(String url, double scale) {
        this.img = new ImageIcon(this.getClass().getResource(url));
        width = (int) (img.getIconWidth() * scale);
        height = (int) (img.getIconHeight() * scale);
    }

    public boolean isDead() {
        return getVida() <= 0;
    }
}
