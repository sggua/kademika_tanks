package im.sgg.ka.jp;

public class T34 extends AbstractTank{

//    public T34() {
//        this.setDelay();
//        this.setRandomPosition();
//        this.direction = RIGHT;
//    }

    public T34(ActionField af, BattleField bf) throws InterruptedException {
        super(af,bf);
    }

    public T34(ActionField af, BattleField bf, int x, int y, To direction) {
        super(af,bf,x,y,direction);
    }



}
