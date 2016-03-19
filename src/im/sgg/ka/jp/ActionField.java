package im.sgg.ka.jp;

import javax.swing.*;
import java.awt.*;

public class ActionField extends JPanel {
    final boolean COLORED_MODE = false;
    public final static To UP = To.UP;
    public final static To DOWN = To.DOWN;
    public final static To LEFT = To.LEFT;
    public final static To RIGHT = To.RIGHT;

    private BattleField bf;
    private Tank tank;
    private Bullet bullet;
    private Aggressor aggressor;

    public void runTheGame() throws Exception {
        this.bf.randomField();
        this.bf.printField();

//        tank.turn(RIGHT);
//        tank.setQuadrantXY(5,5);
//        for (int i=0;i<10;i++) tank.moveRandom();
//        tank.destroy();
//
//        tank = new BT7();
//        for (int i=0;i<10;i++) tank.moveRandom();
//        tank.destroy();
//
//        tank=new Tiger();
//        for (int i=0;i<10;i++) tank.moveRandom();
//        tank.destroy();

        tank= new BT7();
        tank.clean();
        tank.destroy();

    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public void processDestroy(Tank t){
        System.out.println("Destroying the tank("+t.getX()+","+t.getY()+") . . . ");
        t.setX(-100);
        t.setY(-100);
        repaint();

        System.out.println("Done.");
    }

    public void processMove(Tank t) throws InterruptedException {
        System.out.print("Moving from (" + t.getX() + ";" + t.getY() + ")");
        To dir=t.getDirection();
        t.turn(dir);
        repaint();
        if (!t.canMove()) {
            System.out.println("\tCan't move " + getDirectionText(dir));
            return;
        }
        int firstMove = BattleField.QDRNT_SIZE - Tank.FIRST_STEP_DELTA;
        int pixelsToMove = firstMove;
        while (pixelsToMove > 0) {
            if (dir == UP) {
                System.out.print("\tUP   ");
                t.updateY(-Tank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateY(-Tank.FIRST_STEP_DELTA);

            } else if (dir == DOWN) {
                System.out.print("\tDOWN  ");
                t.updateY(Tank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateY(Tank.FIRST_STEP_DELTA);

            } else if (dir == LEFT) {
                System.out.print("\tLEFT  ");
                t.updateX(-Tank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateX(-Tank.FIRST_STEP_DELTA);

            } else if (dir == RIGHT) {
                System.out.print("\tRIGHT ");
                t.updateX(Tank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateX(Tank.FIRST_STEP_DELTA);
            }
            repaint();
            Thread.sleep(t.getDelay());
            pixelsToMove -= Tank.STEP_LENGTH;
        }
        System.out.println("\t" + t.getX() + "_" + t.getX() + "\t\tDone!");

    }

    public void processTurn(Tank t, To dir) {
        t.setDirection(dir);
        repaint();
    }

    public void processTurn180(Tank t){
        switch (t.getDirection()) {
            case LEFT:
                t.setDirection(RIGHT);
                break;
            case RIGHT:
                t.setDirection(LEFT);
                break;
            case UP:
                t.setDirection(DOWN);
                break;
            case DOWN:
                t.setDirection(UP);
                break;
        }
        repaint();
    }

    public void processFire(Bullet b) throws Exception {
        Tank t = this.tank;
        To direction = this.tank.getDirection();
        this.bullet = b;
        this.bullet.setX(t.getX() + 25);
        this.bullet.setY(t.getY() + 25);
        this.bullet.setDirection(direction);
        b.setMissed(false);
        System.out.print("*" + getDirectionText(direction).charAt(0) + "*");
        while (b.isVisible()) {
            if (direction == UP) {
                b.updateY(-Bullet.STEP_LENGTH);
            } else if (direction == DOWN) {
                b.updateY(Bullet.STEP_LENGTH);
            } else if (direction == LEFT) {
                b.updateX(-Bullet.STEP_LENGTH);
            } else if (direction == RIGHT) {
                b.updateX(Bullet.STEP_LENGTH);
            }
            if (b.isVisible()) {
                if (processInterception(bf, b)) b.destroy();
            } else b.setMissed(true);
            repaint();
            Thread.sleep(Bullet.DELAY);
        }
//        return (! b.isMissed());

    }

    public boolean processInterception(BattleField bf, Bullet b) {
        if (b.isInField()) {
            int x = getQuadrantX(b.getX() + Bullet.HALF_SIZE);
            int y = getQuadrantY(b.getY() + Bullet.HALF_SIZE);
            if (!bf.isEmpty(y, x)) {
                bf.updateQuadrant(y, x, "");
                return true;
            }
            if (getQuadrantX(aggressor.getX())==getQuadrantX(bullet.getX())
                    && getQuadrantY(aggressor.getY())==getQuadrantY(bullet.getY())) {
                bf.updateQuadrant(y, x, "");
                aggressor.destroy();
                bullet.destroy();
                return true;
            }

        }
        return false;
    }

    public static String getDirectionText(To way) {
        return String.valueOf(way);
    }

    public static String getQuadrantXY(int v, int h) {                            // x,y => y_x
        return getQuadrantCoordX(v) + "_" + getQuadrantCoordY(h);
    }

    public static int getQuadrantCoordX(int hInt) {
        return getQuadrantCoord(hInt);
    }

    public static int getQuadrantCoordY(int vInt) {
        return getQuadrantCoord(vInt);
    }

    public static int getQuadrantCoord(int qdrnt) {
        return BattleField.QDRNT_SIZE * (qdrnt - 1);
    }

    public String getQuadrant(int x, int y) {
        return y / BattleField.QDRNT_SIZE + "_" + x / BattleField.QDRNT_SIZE;
    }

    public static int getQuadrantX(int x) {
        return x / BattleField.QDRNT_SIZE;
    }

    public static int getQuadrantY(int y) {
        return y / BattleField.QDRNT_SIZE;
    }


//    boolean tankExists(Tank t) {
//        return t == null ? false : true;
//    }
//
//    boolean bulletExists(Bullet b) {
//        return b == null ? false : true;
//    }


/*MAGIC BELOW*/

    public ActionField() throws Exception {
        bf = new BattleField(this);
        tank = new Tank(this, bf);
        bullet = new Bullet(-100, -100);
        aggressor = new Aggressor(this, bf);

        JFrame frame = new JFrame("BATTLE FIELD, DAY 2");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(BattleField.BF_WIDTH + BattleField.BF_BORDER,
                BattleField.BF_HEIGHT + 22 + BattleField.BF_BORDER));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        for (int j = 0; j < bf.getDimentionY(); j++) {
            for (int k = 0; k < bf.getDimentionX(); k++) {
                if (bf.scanQuadrant(j, k).equals("B")) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates.substring(0, separator));
                    int x = Integer.parseInt(coordinates.substring(separator + 1));
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(x, y, 64, 64);
                }
            }
        }


        redrawTank(g, tank);
        redrawTank(g, aggressor);

        g.setColor(new Color(255, 255, 0));
        g.fillRect(bullet.getX(), bullet.getY(), 14, 14);

    }

    private void redrawTank(Graphics g, Tank tank){
        if (tank.getX()>=0 && tank.getY()>=0) {
            // main tank body
            if (tank instanceof Aggressor) g.setColor(new Color(128, 0, 0));
            else g.setColor(new Color(0, 128, 0));
            g.fillRect(tank.getX(), tank.getY(), 64, 64);
            // head of tank
            if (tank instanceof Aggressor) g.setColor(new Color(255, 128, 0));
            else g.setColor(new Color(0, 64, 0));
            if (tank.getDirection() == UP) {
                g.fillRect(tank.getX() + 20, tank.getY(), 24, 34);
            } else if (tank.getDirection() == DOWN) {
                g.fillRect(tank.getX() + 20, tank.getY() + 30, 24, 34);
            } else if (tank.getDirection() == LEFT) {
                g.fillRect(tank.getX(), tank.getY() + 20, 34, 24);
            } else {
                g.fillRect(tank.getX() + 30, tank.getY() + 20, 34, 24);
            }
        }
    }

}
