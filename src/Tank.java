import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {
    private static final int WIDHT = 50;
    private static final int HEIGHT = 50;
    private static final int XSPEED = 2;
    private static final int YSPEED = 2;
    int x;
    int y;
    private int oldX;
    private int oldY;
    private TankClient tc;
    int life = 100;

    enum Direction {
        L, LU, U, RU, R, RD, D, LD, STOP
    }

    private Direction dir = Direction.STOP;
    private Direction gunDir = Direction.D;
    private boolean bL = false, bU = false, bR = false, bD = false;
    boolean good;
    boolean live = true;
    private static Random rand = new Random();
    private int step = rand.nextInt(100) + 20;

    Tank(int x, int y, TankClient tc, boolean good, Direction dir) {
        this.x = x;
        this.y = y;
        oldX = x;
        oldY = y;
        this.tc = tc;
        this.good = good;
        this.dir = dir;
    }

    public void draw(Graphics g) {
        if (!live) {
            tc.enemyTanks.remove(this);
            return;
        }
        Color c = g.getColor();
        if (good)
            g.setColor(Color.red);
        else
            g.setColor(Color.blue);
        g.fillOval(x, y, WIDHT, HEIGHT);
        g.setColor(c);
        move();
        int CenterX = x + WIDHT / 2;
        int CenterY = y + HEIGHT / 2;
        switch (gunDir) {
            case L:
                g.drawLine(CenterX, CenterY, x, y + HEIGHT / 2);
                break;
            case LU:
                g.drawLine(CenterX, CenterY, x, y);
                break;
            case U:
                g.drawLine(CenterX, CenterY, x + WIDHT / 2, y);
                break;
            case RU:
                g.drawLine(CenterX, CenterY, x + WIDHT, y);
                break;
            case R:
                g.drawLine(CenterX, CenterY, x + WIDHT, y + HEIGHT / 2);
                break;
            case RD:
                g.drawLine(CenterX, CenterY, x + WIDHT, y + HEIGHT);
                break;
            case D:
                g.drawLine(CenterX, CenterY, x + WIDHT / 2, y + HEIGHT);
                break;
            case LD:
                g.drawLine(CenterX, CenterY, x, y + HEIGHT);
                break;
            case STOP:
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                bL = true;
                break;
            case KeyEvent.VK_W:
                bU = true;
                break;
            case KeyEvent.VK_D:
                bR = true;
                break;
            case KeyEvent.VK_S:
                bD = true;
                break;
        }
        locateDireciotn();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_F1:
                if (!live) {
                    live = true;
                    life = 100;
                }
                break;
            case KeyEvent.VK_J:
                if (live)
                    fire();
                break;
            case KeyEvent.VK_K:
                if (live)
                    superFire();
                break;
            case KeyEvent.VK_A:
                bL = false;
                break;
            case KeyEvent.VK_W:
                bU = false;
                break;
            case KeyEvent.VK_D:
                bR = false;
                break;
            case KeyEvent.VK_S:
                bD = false;
                break;
        }
        locateDireciotn();
    }

    private void move() {
        oldX = x;
        oldY = y;
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
            case STOP:
                break;
        }
        if (dir != Direction.STOP)
            gunDir = dir;
        if (x < 0)
            x = 0;
        if (x + Tank.WIDHT > TankClient.WIDTH)
            x = TankClient.WIDTH - Tank.WIDHT;
        if (y < 30)
            y = 30;
        if (y + Tank.HEIGHT > TankClient.HEIGHT)
            y = TankClient.HEIGHT - Tank.HEIGHT;
        if (!good) {
            step--;
            if (step == 0) {
                Direction[] dirs = Direction.values();
                int d = rand.nextInt(dirs.length);
                dir = dirs[d];
                step = rand.nextInt(200);
            }
            if (rand.nextInt(1000) > 995)
                fire();
        }
    }

    private void locateDireciotn() {
        if (bL && !bU && !bR && !bD)
            dir = Direction.L;
        else if (bL && bU && !bR && !bD)
            dir = Direction.LU;
        else if (!bL && bU && !bR && !bD)
            dir = Direction.U;
        else if (!bL && bU && bR && !bD)
            dir = Direction.RU;
        else if (!bL && !bU && bR && !bD)
            dir = Direction.R;
        else if (!bL && !bU && bR && bD)
            dir = Direction.RD;
        else if (!bL && !bU && !bR && bD)
            dir = Direction.D;
        else if (bL && !bU && !bR && bD)
            dir = Direction.LD;
        else if (!bL && !bU && !bR && !bD)
            dir = Direction.STOP;
    }

    private void fire() {
        int x = this.x + Tank.WIDHT / 2 - Missile.WIDHT / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y, tc, gunDir, good);
        tc.missiles.add(m);
    }

    public Missile fire(Direction dir) {
        int x = this.x + Tank.WIDHT / 2 - Missile.WIDHT / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y, tc, dir, good);
        tc.missiles.add(m);
        return m;
    }

    private void superFire() {
        int x = this.x + Tank.WIDHT / 2 - Missile.WIDHT / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Direction[] dirs = Direction.values();
        for (int i = 0; i < dirs.length - 1; i++) {
            Missile m = new Missile(x, y, tc, dirs[i], good);
            tc.missiles.add(m);
        }
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDHT, HEIGHT);
    }

    private void recover() {
        x = oldX;
        y = oldY;
    }

    private boolean hitWall(Wall w) {
        if (this.live && this.getRect().intersects(w.getRect())) {
            recover();
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

    public void hitBloodBuff(BloodBuff bbf) {
        if (this.live && bbf.live && this.getRect().intersects(bbf.getRect())) {
            life = 100;
            bbf.live = false;
        }
    }

    private boolean hitTank(Tank t) {
        if (this.live && t.live && this.getRect().intersects(t.getRect()) && this != t) {
            t.recover();
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
}
