package im.sgg.ka.jp;

import java.util.Arrays;


public class BattleField {
    public final boolean COLORED_MODE = false;
    public final static int BF_WIDTH = 576;
    public final static int BF_HEIGHT = 576;
    public final static int BF_BORDER = 10;            // frame.setMinimumSize(new Dimension(BF_WIDTH +10, BF_HEIGHT + 22 +10));
    public final static byte FIELD_SIZE = 9;
    public final static byte QDRNT_SIZE = 64;
    public final static int MAX_BRICKS_QTY = (int) (FIELD_SIZE * FIELD_SIZE / 1.5);

    private static String[][] battleField = new String[FIELD_SIZE][FIELD_SIZE];

    private ActionField af;

    public BattleField(ActionField af) {
        this.af = af;
    }

    public String scanQuadrant(int y, int x) {
        return battleField[y][x];
    }

    public void updateQuadrant(int y, int x, String str) {
        battleField[y][x] = str;
    }

    public int getDimentionX() {
        return battleField != null ? battleField.length : 0;
    }

    public int getDimentionY() {
        if (battleField != null && battleField[0] != null) {
            return battleField[0].length;
        } else return 0;
    }

    public void randomField() {
        clearField();
        for (int i = 0; i < MAX_BRICKS_QTY; i++) {
            int h = intRandom(0, FIELD_SIZE - 1);
            int v = intRandom(0, FIELD_SIZE - 1);
            if (this.isEmpty(h, v)) updateQuadrant(h, v, "B");
        }
    }

    public static boolean isEmptyXY(int x, int y){
        int y2 = ActionField.getQuadrantY(y);
        int x2 = ActionField.getQuadrantX(x);
        return battleField[y2][x2].trim().isEmpty();
    }

    public boolean isEmpty(int row, int line) {
        return row + 1 > FIELD_SIZE || line + 1 > FIELD_SIZE || row < 0 || line < 0 ||
                scanQuadrant(row, line).trim().isEmpty();
    }

    private void clearField() {
        if (!isFilled()) return;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) battleField[i][j] = "";
        }
    }

    public void printField() {
        for (String[] s : battleField)
            System.out.println(Arrays.toString(s).
                    replace("[,", "[ ,").replace(" B", "B").replace(",]", ", ]"));
    }

    public boolean isClean(){
        for (String[] s1:battleField) for (String s2:s1) {
            if (! s2.trim().isEmpty()) return false;
        }
        return true;
    }

    private boolean isFilled() {
        return !(battleField == null || battleField.length < 1 || battleField[0] == null);
    }

    private static int intRandom(int mn, int mx) {
        return (int) (Math.random() * (mx - mn + 1) + mn);
    }
}