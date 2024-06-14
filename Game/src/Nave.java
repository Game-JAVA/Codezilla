import java.awt.*;

public abstract class Nave {
    private int x, y, speedX, speedY, vida, dano;

    public Nave(int x, int y, int speedX, int speedY, int vida, int dano) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.vida = vida;
        this.dano = dano;
    }

    public void moveX(int inc) {
        x+=speedX*inc;
    }

    public void moveY(int inc) {
        y+=speedY*inc;
    }

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
