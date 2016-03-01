package im.sgg.ka.jp;

public class Bullet {
    public final static int DELAY = 2;

    public final static byte UP = ActionField.UP;
    public final static byte DOWN = ActionField.DOWN;
    public final static byte LEFT = ActionField.LEFT;
    public final static byte RIGHT = ActionField.RIGHT;

    public final static int SIZE = 14;                    //	g.fillRect(bX, bY, 14, 14);
    public final static int HALF_SIZE = SIZE / 2;
    public final static byte STEP_LENGTH = 2;

    public final int MIN_COORD = -SIZE - STEP_LENGTH;
    public final int MAX_COORD = BattleField.FIELD_SIZE * BattleField.QDRNT_SIZE + STEP_LENGTH;

    private int x;
    private int y;
    private int direction;
    private boolean missed;

    public Bullet(int x, int y, int direction) {
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

    public int getDirection() {
        return direction;
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
}