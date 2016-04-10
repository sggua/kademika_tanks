package im.sgg.ka.jp;

/**
 * Created by sergiy on 29.02.16.
 * Java Programmer lessons
 * kademika.com
 */
public class Tank extends AbstractTank{

//    public Tank() {
//        this.setDelay();
//        this.setRandomPosition();
//        this.direction = RIGHT;
//    }

    public Tank(ActionField af, BattleField bf) throws InterruptedException {
        super(af,bf);
    }

    public Tank(ActionField af, BattleField bf, int x, int y, To direction) {
        super(af,bf,x,y,direction);
    }



}
