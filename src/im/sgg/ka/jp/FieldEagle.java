package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 12.04.16.
 * Java Developer lessons
 * kademika.com
 */
public class FieldEagle extends FieldBrick{
    public FieldEagle() {
        this(-100,-100);
    }

    public FieldEagle(int x, int y) {
        super(x, y);
        setColor(new Color(220,196,0));
    }
}
