import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 800;
    private static int enemyTanksNum = 10;
    private static int rank = 1;
    Tank myTank = new Tank(100, 100, this, true, Direction.STOP);
    private Image offScreen = null;
    List<Missile> missiles = new ArrayList<>();
    List<Tank> enemyTanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private BloodBar bb = new BloodBar(this);
    private BloodBuff bbf = new BloodBuff(this);

    private TankClient() {
        setTitle("TankWar");
        setSize(WIDTH, HEIGHT);
        setBackground(Color.green);
        setResizable(false);
        Wall w1 = new Wall(150, 100, 100, 300, this);
        Wall w2 = new Wall(300, 300, 300, 100, this);
        walls.add(w1);
        walls.add(w2);
        for (int i = 0; i < 10; i++) {
            Tank t = new Tank(100 + (i + 2) * 100, 100, this, false, Direction.D);
            enemyTanks.add(t);
        }
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new KeyMonitor());
        new Thread(new PaintThread()).run();
    }

    public void paint(Graphics g) {
        g.drawString("missile count: " + missiles.size(), 10, 50);
        g.drawString("tank count: " + enemyTanks.size(), 10, 70);
        for (int i = 0; i < walls.size(); i++) {
            Wall w = walls.get(i);
            w.draw(g);
        }
        myTank.draw(g);
        bb.draw(g);
        bbf.draw(g);
        myTank.hitWalls(walls);
        myTank.hitBloodBuff(bbf);
        for (int i = 0; i < enemyTanks.size(); i++) {
            Tank t = enemyTanks.get(i);
            t.draw(g);
            t.hitWalls(walls);
            t.hitTanks(enemyTanks);
        }
        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.draw(g);
            m.hitTanks(enemyTanks);
            m.hitTank(myTank);
            m.hitWalls(walls);
        }
        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }
        if (enemyTanks.size() == 0) {
            rank++;
            for (int i = 0; i < enemyTanksNum + rank * 3; i++) {
                Tank t = new Tank(100 + (i + 2) * 100, 100, this, false, Direction.D);
                enemyTanks.add(t);
            }
        }
    }

    public void update(Graphics g) {
        if (offScreen == null)
            offScreen = createImage(WIDTH, HEIGHT);
        Graphics gOffSreen = offScreen.getGraphics();
        Color c = gOffSreen.getColor();
        gOffSreen.setColor(Color.green);
        gOffSreen.fillRect(0, 0, WIDTH, HEIGHT);
        gOffSreen.setColor(c);
        paint(gOffSreen);
        g.drawImage(offScreen, 0, 0, null);
    }

    private class KeyMonitor extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }

    private class PaintThread implements Runnable {
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(30);
                    //   System.out.println("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] agrs) {
        new TankClient();
    }
}
