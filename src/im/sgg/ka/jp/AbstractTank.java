package im.sgg.ka.jp;

import javax.swing.*;

/**
 * Created by sergiy on 09.04.16.
 * Java Developer lessons
 * kademika.com
 */
public abstract class AbstractTank extends JFrame {

    public final static int SPEED = 50;
    public final static byte STEP_LENGTH = 7;
    public final static int FIRST_STEP_DELTA = BattleField.QDRNT_SIZE % STEP_LENGTH;
    public final static int MAX_COORD = (BattleField.FIELD_SIZE - 1) * BattleField.QDRNT_SIZE;

    public final static To UP = To.UP;
    public final static To DOWN = To.DOWN;
    public final static To LEFT = To.LEFT;
    public final static To RIGHT = To.RIGHT;

    protected ActionField af;
    protected BattleField bf;
    protected int x, y, delay;
    protected To direction;
    protected Bullet b;




    public void setQuadrantX(int x){
        setX(ActionField.getQuadrantCoordX(x));
    }

    public void setQuadrantY(int y){
        setY(ActionField.getQuadrantCoordY(y));
    }

    public void setQuadrantXY(int x, int y){
        setQuadrantX(x);
        setQuadrantY(y);
    }



    protected void setRandomPosition() {
        int cornerSize = BattleField.FIELD_SIZE;  // size of left down corner
        setQuadrantXY(
                intRandom(1, cornerSize),
                intRandom(BattleField.FIELD_SIZE - cornerSize + 1, BattleField.FIELD_SIZE)
        );
        System.out.println("Random tank coords (x,y): " + this.x + "," + this.y);
    }


    protected int intRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
    protected To dirRandom() throws InterruptedException {
        byte a = (byte) (System.currentTimeMillis() % 10);
        int res;
        if (a < 5) {
            Thread.sleep (a * 10);
            res = (int)(System.currentTimeMillis() % 4) + 1;
        }
        res = (int)(System.currentTimeMillis() % 4) + 1;
        To dir = null;
        switch (res) {
            case 1:
                dir = To.LEFT;              break;
            case 2:
                dir = To.RIGHT;             break;
            case 3:
                dir = To.UP;                break;
            case 4:
                dir = To.DOWN;              break;
        }
        return dir;
    }





    public void setXY(int newX, int newY) {
        setX(newX);
        setY(newY);
    }

    public void updateX(int deltaX){
        this.x+=deltaX;
    }
    public void updateY(int deltaY){
        this.y+=deltaY;
    }

    public void updateXY (int deltaX, int deltaY){
        updateX(deltaX);
        updateY(deltaY);
    }


    public ActionField getAf() {
        return af;
    }

    public void setAf(ActionField af) {
        this.af = af;
    }

    public BattleField getBf() {
        return bf;
    }

    public void setBf(BattleField bf) {
        this.bf = bf;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public To getDirection() {
        return direction;
    }

    public void setDirection(To direction) {
        this.direction = direction;
    }

    public Bullet getB() {
        return b;
    }

    public void setB(Bullet b) {
        this.b = b;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay() {
        this.delay = 5000/SPEED;
    }
    public void setDelay(int newDelay) {
        this.delay = newDelay;
    }

}
