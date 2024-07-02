import java.awt.Graphics;
import java.time.Clock;
import java.util.Random;
import javax.swing.*;

public class Inimigo extends Nave {
    private int width, height, centerY, centerX, raio, screenWidth, screenHeight, rand500, type;
    private double angle;
    private long millisShoot, millisShoot2, millisHorda, millisHorda2;
    private ImageIcon img;
    private Clock clock = Clock.systemDefaultZone();
    private static Random r = new Random();
    private boolean aux, aux2;

    // Construtores
    // Construtor das naves
    public Inimigo(int speedX, int speedY, int vida, double scale, int screenWidth, int screenHeight, String url, int type) {
        super(0, r.nextInt((screenHeight / 3) * 2), speedX, speedY, vida);

        if(type == 2)
            setY(0);

        aux = r.nextBoolean();
        if(aux)
            setX(screenWidth);

        rand500 = r.nextInt(100);
        // randY = r.nextInt(3) - 1;

        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.type = type;

        millisShoot = clock.millis();
        millisShoot2 = millisShoot;
        millisHorda = clock.millis();
        millisHorda2 = millisHorda;
    }

    // Construtor da nave de horda
    public Inimigo(int vida, double scale, int screenWidth, int screenHeight, String url, int type) {
        super(screenWidth, 0, 0, 0, vida);

        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);
        this.raio = screenWidth/2;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.type = type;

        angle = 0;

        // Ajuste para inicializar corretamente o centro do movimento
        centerX = screenWidth / 2;
        centerY = 0;

        millisShoot = clock.millis();
        millisShoot2 = millisShoot;
    }

    // Métodos
    // Desenhar Inimigo
    public void draw(Graphics g) {
        g.drawImage(img.getImage(), getX(), getY(),
                width, height, null);
    }

    // Recebe o dano
    public boolean receberDano(int dano) {
        System.out.println("Acertou");
        setVida(getVida() - dano);
        System.out.println("A vida da nave é " + getVida());
        if(getVida() <= 0)
            return true;
        return false;
    }

    // Movimento reto (nave pequena)
    public boolean movimentSmall() {
        if(getX() < 200 || getX() > (screenWidth - 200) || (getX() % (500 + rand500) != 0) || !aux2) {
            if(!aux) {
                setX(getX() + getSpeedX());
                // setY(getY() + randY);
            }
            else {
                setX(getX() - getSpeedX());
                // setY(getY() + randY);
            }
            aux2 = r.nextBoolean();
            millisHorda = clock.millis();
            millisHorda2 = millisHorda;
        } else {
            millisHorda = clock.millis();

            if((millisHorda - millisHorda2) > 3000)
                aux2 = false;
        }

        if (getX() >= screenWidth || (getX() + width) <= 0)
            return true;
        return false;
    }

    // Movimento triangular (nave média)
    public void movimentMedium() {
        // Calcula a diferença entre o destino e o tiro (cateto)
        int cateto1 = (screenWidth/2) - getX();
        int cateto2 = 300 - getY();

        // Verifica se o objeto já está próximo o suficiente do destino
        if (Math.abs(cateto1) <= getSpeedX() && Math.abs(cateto2) <= getX()) {
            setX(screenWidth/2);
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
    }

    public boolean horda() {
        if(angle < Math.PI) {
            angle += 0.01; // Incrementa o ângulo

            int newX = (int) (centerX + raio * Math.cos(angle));
            int newY = (int) (centerY + raio * Math.sin(angle));

            // Atualiza a posição do objeto
            setX(newX);
            setY(newY);
            return false;
        } else {
            System.out.println("Nave de horda saiu");
            return true;
        }
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
        millisShoot2 = millisShoot;
    }

    public int getType() {
        return type;
    }
}
