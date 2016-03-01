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

        af.processTurn(t,RIGHT);
        t.setQuadrantXY(5,5);
        af.processFire(t);
        af.processTurn180(t);
        af.processTurn(t,UP);
        af.processFire(t);
        af.processTurn180(t);
        af.processFire(t);
        af.processMove(t, RIGHT);
        af.processMove(t, DOWN);
        af.processMove(t, LEFT);
        af.processFire(t);
        af.processTurn180(t);
        af.processFire(t);
        af.processMove(t, UP);

    }
}
