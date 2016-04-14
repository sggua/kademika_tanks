package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 12.04.16.
 * Java Developer lessons
 * kademika.com
 */
public abstract class AbstractField {
    public static final byte SIZE = BattleField.QDRNT_SIZE;
    protected Color color = ActionField.DEFAULT_COLOR;
    protected int x,y;            // real x , real y


////////////////////////////////////////

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
