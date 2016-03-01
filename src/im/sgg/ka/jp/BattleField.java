package im.sgg.ka.jp;

import java.util.Arrays;


public class BattleField {
    public final boolean COLORED_MODE = false;
    public final static int BF_WIDTH = 576;
    public final static int BF_HEIGHT = 576;
    public final static int BF_BORDER = 10;            // frame.setMinimumSize(new Dimension(BF_WIDTH +10, BF_HEIGHT + 22 +10));
    public final static byte FIELD_SIZE = 9;
    public final static byte QDRNT_SIZE = 64;
    final static int MAX_BRICKS_QTY = (int) (FIELD_SIZE * FIELD_SIZE / 1.5);

    private String[][] battleField = new String[FIELD_SIZE][FIELD_SIZE];

    private ActionField af;

    public BattleField(ActionField af) {
        this.af = af;
    }

    public String scanQuadrant(int y, int x) {
        return battleField[y][x];
    }

    public void updateQuadrant(int y, int x, String str) {
        this.battleField[y][x] = str;
    }

    public int getDimentionX() {
        return battleField != null ? battleField.length : 0;
    }

    public int getDimentionY() {
        if (battleField != null && battleField[0] != null) {
            return battleField[0].length;
        } else return 0;
    }

    public void setBattleField(String[][] battleField) {
        this.battleField = battleField;
    }

    public void randomField() {
        clearField();
        for (int i = 0; i < MAX_BRICKS_QTY; i++) {
            int h = intRandom(0, FIELD_SIZE - 1);
            int v = intRandom(0, FIELD_SIZE - 1);
            if (this.isEmpty(h, v)) updateQuadrant(h, v, "B");
        }
    }

    public boolean isEmpty(int row, int line) {
        return row + 1 > FIELD_SIZE || line + 1 > FIELD_SIZE || row < 0 || line < 0 ||
                scanQuadrant(row, line).trim().isEmpty();
    }

    private void clearField() {
        if (!isBattleFieldFilled()) return;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) this.battleField[i][j] = "";
        }
    }

    public void printField() {
        for (String[] s : this.battleField)
            System.out.println(Arrays.toString(s).
                    replace("[,", "[ ,").replace(" B", "B").replace(",]", ", ]"));
    }

    private boolean isBattleFieldFilled() {
        return !(this.battleField == null || this.battleField.length < 1 || this.battleField[0] == null);
    }

    private static int intRandom(int mn, int mx) {
        return (int) (Math.random() * (mx - mn + 1) + mn);
    }
}