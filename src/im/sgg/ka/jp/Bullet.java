package im.sgg.ka.jp;

public class Bullet {
    public final static int DELAY = 5;

    public final static To UP = To.UP;
    public final static To DOWN = To.DOWN;
    public final static To LEFT = To.LEFT;
    public final static To RIGHT = To.RIGHT;


    public final static int SIZE = 14;                    //	g.fillRect(bX, bY, 14, 14);
    public final static int HALF_SIZE = SIZE / 2;
    public final static byte STEP_LENGTH = 2;

    public final int MIN_COORD = -SIZE - STEP_LENGTH;
    public final int MAX_COORD = BattleField.FIELD_SIZE * BattleField.QDRNT_SIZE + STEP_LENGTH;

    private int x;
    private int y;
    private To direction;
    private boolean missed;
    private AbstractTank tank;

    public Bullet(int x, int y, To direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = RIGHT;
    }

    public Bullet(AbstractTank tank) {
        this.x=tank.getX();
        this.y=tank.getY();
        this.direction = tank.getDirection();
        this.tank = tank;
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

    public To getDirection() {
        return direction;
    }

    public void setDirection(To direction) {
        this.direction = direction;
    }

    public void updateX(int deltaX) {
        this.x += deltaX;
    }

    public void updateY(int deltaY) {
        this.y += deltaY;
    }

    boolean isInField() {
        return this.x >= MIN_COORD && this.y >= MIN_COORD &&
                this.x <= MAX_COORD && this.y <= MAX_COORD;
    }

    public void destroy() {
        this.x = -100;
        this.y = -100;
    }

    boolean isVisible() {
        return this.x >= -SIZE && this.y >= -SIZE &&
                this.x <= MAX_COORD + SIZE && this.y <= MAX_COORD + SIZE;
    }

    public boolean isMissed() {
        return this.missed;
    }

    public void setMissed(boolean missed) {
        this.missed = missed;
    }

    public AbstractTank getTank() {
        return tank;
    }

    public void setTank(AbstractTank tank) {
        this.tank = tank;
    }
}