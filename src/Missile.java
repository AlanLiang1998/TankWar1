import java.awt.*;
import java.util.List;

public class Missile {
    public static final int WIDHT = 10;
    public static final int HEIGHT = 10;
    private static final int XSPEED = 2;
    private static final int YSPEED = 2;
    private int x;
    private int y;
    private TankClient tc;
    private Direction dir;
    private boolean live = true;
    private boolean good;
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image missileImage = tk.createImage(Missile.class.getClassLoader().getResource("images/missile/missile.png"));

    Missile(int x, int y, TankClient tc, Direction dir, boolean good) {
        this.x = x;
        this.y = y;
        this.tc = tc;
        this.dir = dir;
        this.good = good;
    }

    public void draw(Graphics g) {
        if (!live) {
            tc.missiles.remove(this);
            return;
        }
        /*Color c = g.getColor();
        if (good)
            g.setColor(Color.YELLOW);
        else
            g.setColor(Color.black);
        g.fillOval(x, y, WIDHT, HEIGHT);
        g.setColor(c);*/
        g.drawImage(missileImage, x, y, null);
        move();
    }

    private void move() {
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
        }
        if (x < 0 || y < 0 || x > TankClient.WIDTH || y > TankClient.HEIGHT) {
            live = false;
        }
    }

    private Rectangle getRect() {
        return new Rectangle(x, y, WIDHT, HEIGHT);
    }

    public boolean hitTank(Tank t) {
        if (this.live && t.live && this.getRect().intersects(t.getRect()) && this.good != t.good) {
            if (t.good) {
                t.life -= 20;
                if (t.life <= 0) {
                    t.live = false;
                }
            } else
                t.live = false;
            this.live = false;
            Explode e = new Explode(x, y, tc);
            tc.explodes.add(e);
            return true;
        }
        return false;
    }

    public void hitTanks(List<Tank> tanks) {
        for (Tank t : tanks) {
            if (this.hitTank(t))
                return;
        }
    }

    private boolean hitWall(Wall w) {
        if (this.live && this.getRect().intersects(w.getRect())) {
            this.live = false;
            Explode e = new Explode(x, y, tc);
            tc.explodes.add(e);
            return true;
        }
        return false;
    }

    public void hitWalls(List<Wall> walls) {
        for (Wall w : walls) {
            if (this.hitWall(w))
                return;
        }
    }
}
