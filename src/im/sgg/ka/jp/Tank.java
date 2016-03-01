package im.sgg.ka.jp;

import javax.swing.*;

/**
 * Created by sergiy on 29.02.16.
 * Java Programmer lessons
 * kademika.com
 */
public class Tank extends JFrame {
    public final static int DELAY = 100;
    public final static byte STEP_LENGTH = 7;
    public final static int FIRST_STEP_DELTA = BattleField.QDRNT_SIZE % STEP_LENGTH;
    public final static To UP = To.UP;
    public final static To DOWN = To.DOWN;
    public final static To LEFT = To.LEFT;
    public final static To RIGHT = To.RIGHT;

    private int x, y;
    private To direction;
    private ActionField af;
    private BattleField bf;
    private Bullet b;

    public final int MAX_COORD = (BattleField.FIELD_SIZE - 1) * BattleField.QDRNT_SIZE;

    public Tank(ActionField af, BattleField bf) {
        this.af = af;
        this.bf = bf;
        this.setRandomPosition();
        this.direction = RIGHT;
    }

    public Tank(ActionField af, BattleField bf, int x, int y, To direction) {
        this.af = af;
        this.bf = bf;
        this.x = x;
        this.y = y;
        this.direction = direction;
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

    public void turn(To direction) {
        this.direction = direction;
        af.processTurn(this, direction);
    }

    public void turn180(){
        af.processTurn180(this);
    }

    void move() throws InterruptedException {
        af.processMove(this);
    }

    void move(To direction) throws InterruptedException {
        this.turn(direction);
        af.processMove(this);
    }

    boolean canMove() {
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

    public void setRandomPosition() {
        int cornerSize = BattleField.FIELD_SIZE;  // size of left down corner
        setQuadrantXY(
                intRandom(1, cornerSize),
                intRandom(BattleField.FIELD_SIZE - cornerSize + 1, BattleField.FIELD_SIZE)
        );
        System.out.println("Random tank coords (x,y): " + this.x + "," + this.y);
    }

    public static int intRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public void moveRandom() {

    }

    public void moveToQuadrant(int v, int h) {

    }
}
