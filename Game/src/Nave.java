import java.awt.*;

public abstract class Nave {
    private int x, y, speedX, speedY, vida;

    public Nave(int x, int y, int speedX, int speedY, int vida) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.vida = vida;
    }

    public void moveX(int inc) {
        x+=speedX*inc;
    }

    public void moveY(int inc) {
        y+=speedY*inc;
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

    // Getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getVida() { return vida; }

    // toString
    @Override
    public String toString() {
        return "Shape{" +
                ", x=" + x +
                ", y=" + y +
                ", speedX=" + speedX +
                ", speedY=" + speedY +
                '}';
    }

    public abstract void draw(Graphics g);
}
