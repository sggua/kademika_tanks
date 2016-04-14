package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 12.04.16.
 * Java Developer lessons
 * kademika.com
 */
public class FieldWater extends FieldFilled {

    public FieldWater() {
        this(-100,-100);
    }

    public FieldWater (int x, int y) {
        super(x,y);
        this.setColor(new Color (90,160,200));
    }

}
