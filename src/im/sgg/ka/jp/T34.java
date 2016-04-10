package im.sgg.ka.jp;

import java.awt.*;

public class T34 extends AbstractTank{

//    public T34() {
//        this.setDelay();
//        this.setRandomPosition();
//        this.direction = RIGHT;
//    }

    public T34(ActionField af, BattleField bf) throws InterruptedException {
        super(af,bf);
        setColorBody(new Color(0, 128, 0));
        setColorHead(new Color(0, 64, 0));
    }

//    public T34(ActionField af, BattleField bf, int x, int y, To direction) {
//        super(af,bf,x,y,direction);
//    }

//    @Override
//    public void draw(Graphics g) {
//        super.draw(g);
//    }
}
