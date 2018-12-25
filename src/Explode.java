import java.awt.*;

public class Explode {
    private int x;
    private int y;
    private TankClient tc;
    private boolean live = true;
    static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] images = {
            tk.createImage(Explode.class.getClassLoader().getResource("images/0.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/1.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/2.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/3.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/4.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/5.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/6.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/7.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/8.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/9.gif")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/10.gif"))
    };
    private int step = 0;

    Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        step++;
        if (!live) {
            tc.explodes.remove(this);
            return;
        }
        if (step >= images.length) {
            step = 0;
            live = false;
        }
        g.drawImage(images[step], x, y, null);
    }
}
