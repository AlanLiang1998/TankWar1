
import java.awt.*;
import java.util.Random;

public class BloodBuff {
    private static final int WIDHT = 50;
    private static final int HEIGHT = 50;
    private int x;
    private int y;
    private TankClient tc;
    boolean live = true;
    private static Random rand = new Random();

    BloodBuff(TankClient tc) {
        this.tc = tc;
        this.x = rand.nextInt(TankClient.WIDTH - WIDHT);
        this.y = rand.nextInt(TankClient.HEIGHT - HEIGHT);
    }

    public void draw(Graphics g) {
        if (!live)
            return;
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, WIDHT, HEIGHT);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDHT, HEIGHT);
    }
}
