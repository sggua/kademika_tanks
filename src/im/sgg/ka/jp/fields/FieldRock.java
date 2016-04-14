package im.sgg.ka.jp.fields;

import im.sgg.ka.jp.tanks.Tiger;

import java.awt.*;

/**
 * Created by sergiy on 12.04.16.
 * Java Developer lessons
 * kademika.com
 */
public class FieldRock extends FieldBrick {
//    private int armor;
    private Tiger ruiner;

    public FieldRock() {
        this(-100,-100);
    }

    public FieldRock(int x, int y) {
        super(x, y);
        setColor(new Color(80,64,64));
//        this.armor=1;
        this.ruiner = new Tiger();
    }
}
