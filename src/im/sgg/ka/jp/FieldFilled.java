package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 12.04.16.
 * Java Developer lessons
 * kademika.com
 */
public abstract class FieldFilled extends AbstractField implements Drawable{

    public FieldFilled() {
    }

    public FieldFilled(int x, int y) {
        this.x=x;
        this.y=y;
    }

    @Override
    public void draw(Graphics g) {
        if (this.x>=0 && this.y>=0) {
            // field color
            g.setColor(this.color);
            g.fillRect(this.x, this.y, this.SIZE, this.SIZE);
        }
    }
}
