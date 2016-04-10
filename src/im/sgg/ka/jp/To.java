package im.sgg.ka.jp;

public enum To {
    LEFT, RIGHT, UP, DOWN;

    public static To random () throws InterruptedException {

        byte a = (byte) (System.currentTimeMillis() % 10);
        int res;
        if (a < 5) {
            Thread.sleep (a * 10);
            res = (int)(System.currentTimeMillis() % 4) + 1;
        }
        res = (int)(System.currentTimeMillis() % 4) + 1;
        To dir = null;
        switch (res) {
            case 1:
                dir = To.LEFT;              break;
            case 2:
                dir = To.RIGHT;             break;
            case 3:
                dir = To.UP;                break;
            case 4:
                dir = To.DOWN;              break;
        }
        return dir;
    }
}
