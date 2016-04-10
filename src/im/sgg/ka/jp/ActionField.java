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
    private T34 defender;
    private Bullet bullet;
    private Tiger aggressor;

    public void runTheGame() throws Exception {
        this.bf.randomField();
        this.bf.printField();

//        defender.turn(RIGHT);
//        defender.setQuadrantXY(5,5);
//        for (int i=0;i<10;i++) defender.moveRandom();
//        defender.destroy();
//
//        defender = new BT7();
//        for (int i=0;i<10;i++) defender.moveRandom();
//        defender.destroy();
//
//        defender=new Tiger();
//        for (int i=0;i<10;i++) defender.moveRandom();
//        defender.destroy();

        defender = new T34(this,bf);
        defender.clean();
        defender.destroy();

    }

    public T34 getDefender() {
        return defender;
    }

    public void setDefender(T34 defender) {
        this.defender = defender;
    }

    public void processDestroy(AbstractTank t){
        System.out.println("Destroying the tank("+t.getX()+","+t.getY()+") . . . ");
        t.setX(-100);
        t.setY(-100);
        repaint();

        System.out.println("Done.");
    }

    public void processMove(AbstractTank t) throws InterruptedException {
        System.out.print("Moving from (" + t.getX() + ";" + t.getY() + ")");
        To dir=t.getDirection();
        t.turn(dir);
        repaint();
        if (!t.canMove()) {
            System.out.println("\tCan't move " + getDirectionText(dir));
            return;
        }
        int firstMove = BattleField.QDRNT_SIZE - AbstractTank.FIRST_STEP_DELTA;
        int pixelsToMove = firstMove;
        while (pixelsToMove > 0) {
            if (dir == UP) {
                System.out.print("\tUP   ");
                t.updateY(-AbstractTank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateY(-AbstractTank.FIRST_STEP_DELTA);

            } else if (dir == DOWN) {
                System.out.print("\tDOWN  ");
                t.updateY(AbstractTank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateY(AbstractTank.FIRST_STEP_DELTA);

            } else if (dir == LEFT) {
                System.out.print("\tLEFT  ");
                t.updateX(-AbstractTank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateX(-AbstractTank.FIRST_STEP_DELTA);

            } else if (dir == RIGHT) {
                System.out.print("\tRIGHT ");
                t.updateX(AbstractTank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateX(AbstractTank.FIRST_STEP_DELTA);
            }
            repaint();
            Thread.sleep(t.getDelay());
            pixelsToMove -= AbstractTank.STEP_LENGTH;
        }
        System.out.println("\t" + t.getX() + "_" + t.getX() + "\t\tDone!");

    }

    public void processTurn(AbstractTank t, To dir) {
        t.setDirection(dir);
        repaint();
    }

    public void processTurn180(AbstractTank t){
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
        AbstractTank t = this.defender;
        To direction = this.defender.getDirection();
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

    public boolean processInterception(BattleField bf, Bullet b) throws InterruptedException {
        if (b.isInField()) {
            int xB = getQuadrantX(b.getX() + Bullet.HALF_SIZE);
            int yB = getQuadrantY(b.getY() + Bullet.HALF_SIZE);
            if (!bf.isEmpty(yB, xB)) {
                bf.updateQuadrant(yB, xB, "");
                return true;
            }
            if (checkInterception(aggressor,b)) {
                aggressor = new Tiger(this,bf);
                Thread.sleep(3000);
                repaint(); return true;
            }
            if (checkInterception(defender,b)) {
                defender = new T34(this,bf);
                Thread.sleep(3000);
                repaint(); return true;
            }

        }
        return false;
    }

    private boolean checkInterception(AbstractTank t, Bullet b){
        if (b.getTank().equals(t)) return false;
        int armor =0;
        if (t instanceof Tiger) {
            armor = ((Tiger) t).getArmor();
        }
        int xB = getQuadrantX(b.getX() + Bullet.HALF_SIZE);
        int yB = getQuadrantY(b.getY() + Bullet.HALF_SIZE);
        int xT = getQuadrantX(t.getX());
        int yT = getQuadrantY(t.getY());
        if (xT==xB && yT==yB) {
            System.out.println("Fired at "+xT+","+yT);
            bf.updateQuadrant(yB, xB, "");
            b.destroy();
            if (armor==0) t.destroy();
            else {
                ((Tiger) t).setArmor(((Tiger) t).getArmor()-1);
                System.out.println("Armor decreased to "+((Tiger) t).getArmor());
                return false;
            }
            return true;
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

    public static int intRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

//    boolean tankExists(AbstractTank t) {
//        return t == null ? false : true;
//    }
//
//    boolean bulletExists(Bullet b) {
//        return b == null ? false : true;
//    }


/*MAGIC BELOW*/

    public ActionField() throws Exception {
        bf = new BattleField(this);
        defender = new T34(this, bf);
        bullet = new Bullet(-100, -100);
        aggressor = new Tiger(this, bf);

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


        defender.draw(g);
        aggressor.draw(g);
        bullet.draw(g);

    }

//    private void redrawBullet(Graphics g, Bullet bullet){
//        // Light orange
//        g.setColor(new Color(240, 160, 0));
//        g.fillRect(bullet.getX()+4, bullet.getY()+2, 6, 10);
//        g.fillRect(bullet.getX()+2, bullet.getY()+4, 10, 6);
//        // Dark orange
//        g.setColor(new Color(240, 144, 0));
//        g.fillRect(bullet.getX()+6, bullet.getY()+2, 2, 2);
//        g.fillRect(bullet.getX()+2, bullet.getY()+6, 2, 2);
//        g.fillRect(bullet.getX()+10, bullet.getY()+6, 2, 2);
//        g.fillRect(bullet.getX()+6, bullet.getY()+10, 2, 2);
//        // Light yellow
//        g.setColor(new Color(255, 255, 128));
//        g.fillRect(bullet.getX()+4, bullet.getY()+4, 6, 6);
//
//
//    }

//    private void redrawTank(Graphics g, AbstractTank tank){
//        if (tank.getX()>=0 && tank.getY()>=0) {
//            // main defender body
//            if (tank instanceof Aggressor) g.setColor(new Color(128, 0, 0));
//            else g.setColor(new Color(0, 128, 0));
//            g.fillRect(tank.getX(), tank.getY(), 64, 64);
//            // head of defender
//            if (tank instanceof Aggressor) g.setColor(new Color(255, 128, 0));
//            else g.setColor(new Color(0, 64, 0));
//            if (tank.getDirection() == UP) {
//                g.fillRect(tank.getX() + 20, tank.getY(), 24, 34);
//            } else if (tank.getDirection() == DOWN) {
//                g.fillRect(tank.getX() + 20, tank.getY() + 30, 24, 34);
//            } else if (tank.getDirection() == LEFT) {
//                g.fillRect(tank.getX(), tank.getY() + 20, 34, 24);
//            } else {
//                g.fillRect(tank.getX() + 30, tank.getY() + 20, 34, 24);
//            }
//        }
//    }

}
