package im.sgg.ka.jp.fields;

import im.sgg.ka.jp.interfaces.Destroyable;

import java.awt.*;

/**
 * Created by sergiy on 12.04.16.
 * Java Developer lessons
 * kademika.com
 */
public class FieldBrick extends FieldFilled implements Destroyable {
    public FieldBrick() {
        this(-100,-100);
    }

    public FieldBrick(int x, int y) {
        super(x, y);
        setColor(new Color(172,48,0));
    }

    @Override
    public void destroy() {
        this.x=-100;
        this.y=-100;
    }
}
