import java.awt.*;

public class Explode {
    private int x;
    private int y;
    private TankClient tc;
    private boolean live = true;
    static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] images = {
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(1).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(2).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(3).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(4).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(5).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(6).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(7).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(8).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(9).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(10).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(11).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(12).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(13).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(14).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(15).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(16).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(17).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(18).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(19).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(20).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(21).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(22).png")),
            tk.createImage(Explode.class.getClassLoader().getResource("images/explode/(23).png")),
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
