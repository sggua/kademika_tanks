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
    public final static byte UP = ActionField.UP;
    public final static byte DOWN = ActionField.DOWN;
    public final static byte LEFT = ActionField.LEFT;
    public final static byte RIGHT = ActionField.RIGHT;

    private int x, y, direction = 1;
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

    public Tank(ActionField af, BattleField bf, int x, int y, int direction) {
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

    public void updateX(int x) {
        this.x = x;
    }

    public void updateY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void turn(int direction) {
        this.direction = direction;
        af.processTurn(this, direction);
    }

    void move(int direction) throws InterruptedException {
        this.direction = direction;
        af.processMove(this, direction);
    }

    boolean canMove(int direction) {
        return !((direction == UP && this.y < BattleField.QDRNT_SIZE) ||
                (direction == DOWN && this.y + BattleField.QDRNT_SIZE > MAX_COORD) ||
                (direction == LEFT && this.x < BattleField.QDRNT_SIZE) ||
                (direction == RIGHT && this.x + BattleField.QDRNT_SIZE > MAX_COORD));
    }

    public void fire(Bullet b) throws Exception {
        this.b = b;
        af.processFire(this,b);
    }

    public void setRandomPosition() {
        int cornerSize = BattleField.FIELD_SIZE;  // size of left down corner
        this.x = ActionField.getQuadrantCoordX(
                intRandom(1, cornerSize)
        );
        this.y = ActionField.getQuadrantCoordY(
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
