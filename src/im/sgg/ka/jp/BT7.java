package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 10.03.16.
 * Java Developer lessons
 * kademika.com
 */
public class BT7 extends Tank {
    public BT7() throws HeadlessException {
    }

    @Override
    public void setDelay() {
        super.setDelay();
        this.setDelay(getDelay()/2);
    }
}
