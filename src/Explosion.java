import javax.swing.*;
import java.awt.*;
import java.time.Clock;

public class Explosion {
    private Clock clock = Clock.systemDefaultZone();
    private long millis, millis2;
    private ImageIcon img;
    private int x, y, width, height;

    public Explosion(int x, int y, String url, double scale) {
        this.x = x;
        this.y = y;
        this.img = new ImageIcon(this.getClass().getResource(url));
        this.width = (int) (img.getIconWidth() * scale);
        this.height = (int) (img.getIconHeight() * scale);

        millis = clock.millis();
        millis2 = millis;
    }

    public void draw(Graphics g) {
        g.drawImage(img.getImage(), x, y, width, height, null);
    }

    public long getMillis() {
        millis = clock.millis();
        return millis;
    }

    public long getMillis2() {
        return millis2;
    }
}
