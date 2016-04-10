package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 10.03.16.
 * Java Developer lessons
 * kademika.com
 */
public class BT7 extends Tank {

//    public BT7() {
//        super();
//    }

    public BT7(ActionField af, BattleField bf) {
        super(af,bf);
        this.setDelay();
    }

    @Override
    public void setDelay() {
        super.setDelay();
        this.setDelay(getDelay()/2);
    }
}
