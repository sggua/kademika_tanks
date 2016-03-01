package im.sgg.ka.jp;

import javax.swing.*;
import java.awt.*;

public class ActionField extends JPanel {
    final boolean COLORED_MODE = false;
    public final static byte UP = 1;
    public final static byte DOWN = 2;
    public final static byte LEFT = 3;
    public final static byte RIGHT = 4;

    private static String[] directionText = {"","UP","DOWN","LEFT","RIGHT"};
        private BattleField bf;
    private Tank tank;
    private Bullet bullet;

    public void runTheGame(){
        this.bf.randomField();
        this.bf.printField();
    }

    public void processMove(Tank t, int dir) throws InterruptedException {
        System.out.print("Moving from (" + t.getX() + ";" + t.getY() + ")");
        processTurn(t, dir);
        repaint();
        if (! t.canMove(dir)){
            System.out.println("\tCan't move "+ directionText[dir]);
            return;
        }
        int firstMove= BattleField.QDRNT_SIZE- Tank.FIRST_STEP_DELTA;
        int pixelsToMove=firstMove;
        while (pixelsToMove>0) {
            if (dir == UP) {
                System.out.print("\tUP   ");
                t.updateY( t.getY() - Tank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateY(t.getY()-Tank.FIRST_STEP_DELTA);

            } else if (dir == DOWN) {
                System.out.print("\tDOWN  ");
                t.updateY( t.getY() + Tank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateY(t.getY()+Tank.FIRST_STEP_DELTA);

            } else if (dir == LEFT) {
                System.out.print("\tLEFT  ");
                t.updateX( t.getX() - Tank.STEP_LENGTH);
                if (pixelsToMove == firstMove) t.updateX(t.getX()-Tank.FIRST_STEP_DELTA);

            } else if (dir == RIGHT) {
                System.out.print("\tRIGHT ");
                t.updateX( t.getX() + Tank.STEP_LENGTH);
                if (pixelsToMove == firstMove)  t.updateX(t.getX()+Tank.FIRST_STEP_DELTA);
            }
            repaint(); Thread.sleep(Tank.DELAY);
            pixelsToMove-= Tank.STEP_LENGTH;
        }
        System.out.println("\t"+ t.getX() + "_" + t.getX() +"\t\tDone!");

    }

    public void processTurn(Tank t, int dir){
        t.setDirection(dir);
        repaint();
    }
    public void processFire(Tank t, Bullet b) throws Exception {
        int direction=t.getDirection();
        b = new Bullet(t.getX()+25, t.getY() +25, direction);
        b.setMissed(false);
        System.out.print("*"+ getDirectionText(direction).charAt(0)+"*");
        while (b.isVisible()) {
            if (direction == UP) 	      { b.setY(b.getY()- Bullet.STEP_LENGTH);
            } else if (direction == DOWN)  {	b.setY(b.getY()+ Bullet.STEP_LENGTH);
            } else if (direction == LEFT)  {	b.setX(b.getX()- Bullet.STEP_LENGTH);
            } else if (direction == RIGHT) {	b.setX(b.getX()+ Bullet.STEP_LENGTH);}
            if (b.isVisible()) {
                if (processInterception(bf,b)) b.destroy();
            }
            else b.setMissed(true);
            repaint();
            Thread.sleep(Bullet.DELAY);
        }
//        return (! b.isMissed());
        return;

    }
    public boolean processInterception(BattleField bf, Bullet b) {
        if (b.isInField()) {
            int x= getQuadrantX(b.getX()+Bullet.HALF_SIZE);
            int y= getQuadrantY(b.getY()+Bullet.HALF_SIZE);
            if (! bf.isEmpty(y,x)){
                bf.updateQuadrant(y,x,"");
                return true;
            }
        }
        return false;
    }
    public static String getDirectionText(int n) {
        if (directionText != null && n < directionText.length) return directionText[n];
        else return "";
    }

    public static String getQuadrantXY(int v, int h) {       						// x,y => y_x
        return getQuadrantCoordX(v)+"_"+getQuadrantCoordY(h);
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
        return y/BattleField.QDRNT_SIZE+"_"+x/BattleField.QDRNT_SIZE;
    }

    public static int getQuadrantX(int x) {
        return x/BattleField.QDRNT_SIZE;
    }
    public static int getQuadrantY(int y) {
        return y/BattleField.QDRNT_SIZE;
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
        tank = new Tank(this,bf);
        bullet = new Bullet(-100,-100, -1);

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
                if (bf.scanQuadrant(j,k).equals("B")) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates.substring(0, separator));
                    int x = Integer.parseInt(coordinates.substring(separator + 1));
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(x, y, 64, 64);
                }
            }
        }

//        if (tankExists(tank)) {                           // if tank exists
//            System.out.println("\tTank coords (X, Y):"+tank.getX()+","+ tank.getY());
            g.setColor(new Color(255, 0, 0));
            g.fillRect(tank.getX(), tank.getY(), 64, 64);

            g.setColor(new Color(0, 255, 0));
            if (tank.getDirection() == 1) {
                g.fillRect(tank.getX() + 20, tank.getY(), 24, 34);
            } else if (tank.getDirection() == 2) {
                g.fillRect(tank.getX() + 20, tank.getY() + 30, 24, 34);
            } else if (tank.getDirection() == 3) {
                g.fillRect(tank.getX(), tank.getY() + 20, 34, 24);
            } else {
                g.fillRect(tank.getX() + 30, tank.getY() + 20, 34, 24);
            }
//        }
//        else System.out.println("No tank");

//        if (bulletExists(bullet)) {                           // if bullet exists
            g.setColor(new Color(255, 255, 0));
            g.fillRect(bullet.getX(), bullet.getY(), 14, 14);
//        }
//        else System.out.println("No bullet");
    }

}
