import java.awt.*;

public class BloodBar {
    private static final int WIDHT = 50;
    private static final int HEIGHT = 10;
    private TankClient tc;

    BloodBar(TankClient tc) {
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if (!tc.myTank.live)
            return;
        Color c = g.getColor();
        g.setColor(Color.RED);
        int x = tc.myTank.x;
        int y = tc.myTank.y - 20;
        g.drawRect(x, y, WIDHT, HEIGHT);
        g.setColor(Color.magenta);
        int lifeWidth = tc.myTank.life * WIDHT / 100;
        g.fillRect(x, y, lifeWidth, HEIGHT);
        g.setColor(c);
    }
}
