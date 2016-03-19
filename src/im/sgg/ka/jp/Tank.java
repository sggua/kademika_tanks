package im.sgg.ka.jp;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sergiy on 29.02.16.
 * Java Programmer lessons
 * kademika.com
 */
public class Tank extends JFrame {
    private final static int SPEED = 50;
    public final static byte STEP_LENGTH = 7;
    public final static int FIRST_STEP_DELTA = BattleField.QDRNT_SIZE % STEP_LENGTH;
    public final static To UP = To.UP;
    public final static To DOWN = To.DOWN;
    public final static To LEFT = To.LEFT;
    public final static To RIGHT = To.RIGHT;

    private static int delay;
    private int x, y;
    private To direction;
    private static ActionField af;
    private static BattleField bf;
    private Bullet b;

    public final int MAX_COORD = (BattleField.FIELD_SIZE - 1) * BattleField.QDRNT_SIZE;


    public Tank() throws HeadlessException {
        this.setDelay();
        this.setRandomPosition();
        this.direction = RIGHT;
    }

    public Tank(ActionField af, BattleField bf) {
        this.setDelay();
        this.af = af;
        this.bf = bf;
        this.setRandomPosition();
        this.direction = RIGHT;
    }

    public Tank(ActionField af, BattleField bf, int x, int y, To direction) {
        this.setDelay();
        this.af = af;
        this.bf = bf;
        this.x = x;
        this.y = y;
        this.direction = direction;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        setX(x);
        setY(y);
    }

    public void updateX(int delta){
        this.x+=delta;
    }
    public void updateY(int delta){
        this.y+=delta;
    }

    public void updateXY (int deltaX, int deltaY){
        updateX(deltaX);
        updateY(deltaY);
    }

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


    public To getDirection() {
        return direction;
    }

    public void setDirection(To direction) {
        this.direction = direction;
    }


    public void clean() throws Exception{
        while (! bf.isClean()) {
            for (int i=1;i<BattleField.FIELD_SIZE && !bf.isClean();i++) {
                moveToQuadrant(BattleField.FIELD_SIZE+1-i, i);
                if (i<3) {
                    turn(UP); fire();
                    turn(RIGHT); fire();
                } else {
                    clean(UP);
                    clean(RIGHT);
                    clean(DOWN);
                    clean(LEFT);
                }
            }
            if (!bf.isClean()){
                moveToQuadrant(1, BattleField.FIELD_SIZE);
                clean(DOWN);
                clean(LEFT);
            }

        }
        System.out.println("Cleaned!");
    }
    private void clean (To side) throws Exception{
        turn(side); do fire();	while (! this.b.isMissed());
    }
    public void turn(To direction) {
        this.direction = direction;
        af.processTurn(this, direction);
    }

    public void turn180(){
        af.processTurn180(this);
    }

    public void move() throws InterruptedException {
        af.processMove(this);
    }

    public void move(To direction) throws InterruptedException {
        this.turn(direction);
        af.processMove(this);
    }

    public boolean canMove() {
        To direction = this.getDirection();
        return !((direction == UP && this.y < BattleField.QDRNT_SIZE) ||
                (direction == DOWN && this.y + BattleField.QDRNT_SIZE > MAX_COORD) ||
                (direction == LEFT && this.x < BattleField.QDRNT_SIZE) ||
                (direction == RIGHT && this.x + BattleField.QDRNT_SIZE > MAX_COORD));
    }

    public void fire() throws Exception {
        this.b = new Bullet(this);
        af.processFire(b);
    }

    private void setRandomPosition() {
        int cornerSize = BattleField.FIELD_SIZE;  // size of left down corner
        setQuadrantXY(
                intRandom(1, cornerSize),
                intRandom(BattleField.FIELD_SIZE - cornerSize + 1, BattleField.FIELD_SIZE)
        );
        System.out.println("Random tank coords (x,y): " + this.x + "," + this.y);
    }

    public void destroy(){
        af.processDestroy(this);
    }

    protected int intRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
    private To dirRandom() throws InterruptedException {
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

    public void moveRandom() throws Exception {
        To rnd=dirRandom();
        turn(rnd);
        if (this.canMove()) {
            clearTheWay(rnd);
            System.out.print("rnd");
            move(rnd);
        }
    }

    public void moveToQuadrant(int v, int h) throws Exception {
        System.out.print("Current  coords: ("+this.getX()+";"+this.getY()+")\t");
        if (v<1 || h<1 || v>BattleField.FIELD_SIZE || h> BattleField.FIELD_SIZE) {
            System.out.println("Can't move to\tv="+v+" h="+h);
            return;
        }

        int goalX = ActionField.getQuadrantCoordX(h);
        int goalY = ActionField.getQuadrantCoordY(v);

        System.out.println("\t\t\t\tv="+v+" h="+h+" goalX="+goalX+" goalY="+goalY);

        int moveX = goalX-this.getX();
        int moveY = goalY-this.getY();
//        To direction=this.getDirection();
        while ((moveX!=0) || (moveY!=0)) {
            if (moveX!=0){   			// while - line; if - zigzag
                if ( moveX > 0 ) {
                    setDirection(RIGHT);
                } else {
                    setDirection(LEFT);
                }
                clearTheWay(direction);
                this.move(direction);
                moveX = goalX-this.getX();
            }
            if (moveY!=0){				// while - line; if - zigzag
                if ( moveY > 0 ) {
                    setDirection(DOWN);
                } else { setDirection(UP);	}
                clearTheWay(direction);
                this.move(direction);
                moveY = goalY-this.getY();
            }
        }
        System.out.println("Moved to ("+this.getX()+";"+this.getY()+")");
    }
    private void clearTheWay(To direction) throws Exception {
        turn(direction);repaint();
        if (	//! BattleField.isEmptyXY(this.x, this.y) ||
                (direction==UP && ! bf.isEmptyXY(this.x, this.y-1)) ||
                (direction==DOWN && ! bf.isEmptyXY(this.x, this.y+BattleField.QDRNT_SIZE)) ||
                (direction==LEFT && ! bf.isEmptyXY(this.x-1, this.y)) ||
                (direction==RIGHT && ! bf.isEmptyXY(this.x+BattleField.QDRNT_SIZE, this.y))
                ) {
            System.out.println("Clear the way "+String.valueOf(direction)+"!");
            fire();
        }
    }

}
