import java.awt.*;

public class Explode {
    private int x;
    private int y;
    private TankClient tc;
    private boolean live = true;
    private int[] diameter;
    private int step = 0;

    Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
        diameter = new int[101];
        for (int i = 0; i < 50; i++)
            diameter[i] = i;
        for (int i = 50; i <= 100; i++)
            diameter[i] = 100 - i;
    }

    public void draw(Graphics g) {
        if (!live) {
            tc.explodes.remove(this);
            return;
        }
        if (step >= diameter.length) {
            step = 0;
            live = false;
        }
        Color c = g.getColor();
        g.setColor(Color.orange);
        g.fillOval(x, y, diameter[step], diameter[step]);
        g.setColor(c);
        step++;
    }
}
