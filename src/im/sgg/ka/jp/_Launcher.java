package im.sgg.ka.jp;

public class _Launcher {

    final static To UP = To.UP;
    final static To DOWN = To.DOWN;
    final static To LEFT = To.LEFT;
    final static To RIGHT = To.RIGHT;


    public static void main(String[] args) throws Exception {

        ActionField af = new ActionField();
        af.runTheGame();

        Tank t = af.getTank();

        t.turn(RIGHT);
        t.setQuadrantXY(5,5);
        t.fire();
        t.turn180();
        t.turn(UP);
        t.fire();
        t.turn180();
        t.fire();
        t.move(RIGHT);
        t.move(DOWN);
        t.move(LEFT);
        t.fire();
        t.turn180();
        t.fire();
        t.move();
        t.fire();
        t.move();
        t.fire();
        t.move();
        t.fire();

    }
}
