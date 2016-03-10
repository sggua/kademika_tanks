package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 10.03.16.
 * Java Developer lessons
 * kademika.com
 */
public class Tiger extends Tank {
    private int armor;


    public Tiger() throws HeadlessException {
        this(1);
    }

    public Tiger(int armor) throws HeadlessException {
        this.armor = armor;
    }
}
