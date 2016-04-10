package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 10.03.16.
 * Java Developer lessons
 * kademika.com
 */
public class BT7 extends AbstractTank {

//    public BT7() {
//        super();
//    }

    public BT7(ActionField af, BattleField bf) throws InterruptedException {
        super(af,bf);
        this.setDelay();
        setColorBody(new Color(64, 128, 32));
        setColorHead(new Color(64, 96, 0));

    }

    @Override
    public void setDelay() {
        super.setDelay();
        this.setDelay(getDelay()/2);
    }
}
