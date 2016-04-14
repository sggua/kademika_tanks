package im.sgg.ka.jp.abstracts;

import im.sgg.ka.jp.*;
import im.sgg.ka.jp.enums.To;
import im.sgg.ka.jp.interfaces.Destroyable;
import im.sgg.ka.jp.interfaces.Drawable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sergiy on 09.04.16.
 * Java Developer lessons
 * kademika.com
 */
public abstract class AbstractTank extends JFrame implements Drawable, Destroyable {

    public final static int SPEED = 70;
    public final static byte STEP_LENGTH = 7;
    public final static int FIRST_STEP_DELTA = BattleField.QDRNT_SIZE % STEP_LENGTH;
    public final static int MAX_COORD = (BattleField.FIELD_SIZE - 1) * BattleField.QDRNT_SIZE;
    public final static int CORNER_SIZE = BattleField.FIELD_SIZE;  // size of left down corner

    public final static To UP = To.UP;
    public final static To DOWN = To.DOWN;
    public final static To LEFT = To.LEFT;
    public final static To RIGHT = To.RIGHT;

    private ActionField af;
    private BattleField bf;
    private int x, y, delay;
    private To direction;
    private Bullet b;
    private Color colorBody = new Color(0, 128, 128);
    private Color colorHead = new Color(0, 64, 64);

    public AbstractTank() throws HeadlessException {
    }

    public AbstractTank(ActionField af, BattleField bf) throws HeadlessException, InterruptedException {
        this(af,bf,randomX(),randomY());
    }

    public AbstractTank(ActionField af, BattleField bf, int x, int y) throws HeadlessException, InterruptedException {
        this(af,bf,x,y,To.random());
    }

    public AbstractTank(ActionField af, BattleField bf, int x, int y, To direction) throws HeadlessException {
        this.af = af;
        this.bf = bf;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.setDelay();
        System.out.println("Tank coords (x,y): " + this.x + "," + this.y);
    }

    ////////////////////////////////////////////////////////


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

//    public void turn180(){
//        af.processTurn180(this);
//    }

    public void moveRandom() throws Exception {
        To rnd=To.random();
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

    public void destroy(){
//        if (af!=null)
        af.processDestroy(this);
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



//    protected void setRandomPosition() {
//        setQuadrantXY(
//                intRandom(1, CORNER_SIZE),
//                intRandom(BattleField.FIELD_SIZE - CORNER_SIZE + 1, BattleField.FIELD_SIZE)
//        );
//        System.out.println("Random tank coords (x,y): " + this.x + "," + this.y);
//    }


    private static int randomX(){
        return ActionField.getQuadrantCoordX(ActionField.intRandom(1, CORNER_SIZE));
    }

    private static int randomY(){
        return ActionField.getQuadrantCoordY(
                ActionField.intRandom(BattleField.FIELD_SIZE - CORNER_SIZE + 1, BattleField.FIELD_SIZE)
        );
    }


//    protected To dirRandom() throws InterruptedException {
//        byte a = (byte) (System.currentTimeMillis() % 10);
//        int res;
//        if (a < 5) {
//            Thread.sleep (a * 10);
//            res = (int)(System.currentTimeMillis() % 4) + 1;
//        }
//        res = (int)(System.currentTimeMillis() % 4) + 1;
//        To dir = null;
//        switch (res) {
//            case 1:
//                dir = To.LEFT;              break;
//            case 2:
//                dir = To.RIGHT;             break;
//            case 3:
//                dir = To.UP;                break;
//            case 4:
//                dir = To.DOWN;              break;
//        }
//        return dir;
//    }





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

    public void draw(Graphics g){

        if (x>=0 && y>=0) {
            // tank body color
            g.setColor(colorBody);
            g.fillRect(x, y, 64, 64);

            // tank head color
            g.setColor(colorHead);
            if (this.getDirection() == UP) {           g.fillRect(x + 20, y, 24, 34);          // UP
            } else if (this.getDirection() == DOWN) {  g.fillRect(x + 20, y + 30, 24, 34);     // DOWN
            } else if (this.getDirection() == LEFT) {  g.fillRect(x, y + 20, 34, 24);          // LEFT
            } else {                                   g.fillRect(x + 30, y + 20, 34, 24);     // RIGHT
            }
        }
    }


//////////////////////////////////////////////////////////

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

    public Color getColorHead() {
        return colorHead;
    }

    public void setColorHead(Color colorHead) {
        this.colorHead = colorHead;
    }

    public Color getColorBody() {
        return colorBody;
    }

    public void setColorBody(Color colorBody) {
        this.colorBody = colorBody;
    }
}
