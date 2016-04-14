package im.sgg.ka.jp;

import im.sgg.ka.jp.fields.FieldBrick;
import im.sgg.ka.jp.fields.FieldEagle;
import im.sgg.ka.jp.fields.FieldRock;
import im.sgg.ka.jp.fields.FieldWater;

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

    private FieldBrick[][] fieldBricks = new FieldBrick[FIELD_SIZE][FIELD_SIZE];
    private FieldWater[][] fieldWaters = new FieldWater[FIELD_SIZE][FIELD_SIZE];
    private FieldRock[][] fieldRocks = new FieldRock[FIELD_SIZE][FIELD_SIZE];
    private FieldEagle fieldEagle = new FieldEagle();

    private ActionField af;

    public BattleField(ActionField af) {
        this.af = af;
        this.battleField = new String[][]{
                {" ", " ", " ", " ", " ", " ", " ", "B", " "},
                {" ", "B", "W", "R", " ", "B", " ", " ", " "},
                {" ", " ", "B", "W", "W", "W", " ", "R", " "},
                {" ", " ", " ", " ", "W", "W", "B", " ", " "},
                {" ", "B", " ", " ", " ", " ", " ", " ", " "},
                {"R", "W", " ", " ", " ", " ", " ", "B", "R"},
                {" ", "W", " ", "B", "B", "B", " ", " ", " "},
                {" ", "B", " ", "B", " ", "B", " ", " ", " "},
                {" ", " ", " ", "B", "E", "B", " ", "R", "B"},
        };
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

    public boolean isEmptyXY(int x, int y){
        if (x<0 || y<0) return true;
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
                    replace("[,", "[ ,")
                    .replace(" B", "B")
                    .replace(" R", "R")
                    .replace(" W", "W")
                    .replace(" E", "E")
                    .replace(",B", ", B")
                    .replace(",R", ", R")
                    .replace(",W", ", W")
                    .replace(",E", ", E")
                    .replace(",]", ", ]")
            );
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

    public void initField() {
        for (int i=0; i< FIELD_SIZE; i++ ) {
            for (int j=0; j<FIELD_SIZE; j++) {
                if (battleField[i][j].equals("B")) {
                    this.fieldBricks[i][j] = new FieldBrick(
                            ActionField.getQuadrantCoordY(j+1),ActionField.getQuadrantCoordX(i+1)
                    );
                } else if (battleField[i][j].equals("R")) {
                    this.fieldRocks[i][j] = new FieldRock(
                            ActionField.getQuadrantCoordY(j+1),ActionField.getQuadrantCoordX(i+1)
                    );
                } else if (battleField[i][j].equals("W")) {
                    this.fieldWaters[i][j] = new FieldWater(
                            ActionField.getQuadrantCoordY(j+1),ActionField.getQuadrantCoordX(i+1)
                    );
                } else if (battleField[i][j].equals("E")) {
                    this.fieldEagle = new FieldEagle(
                            ActionField.getQuadrantCoordY(j+1),ActionField.getQuadrantCoordX(i+1)
                    );
                }

            }

        }
    }

    public static String[][] getBattleField() {
        return battleField;
    }

    public static void setBattleField(String[][] battleField) {
        BattleField.battleField = battleField;
    }

    public FieldBrick[][] getFieldBricks() {
        return fieldBricks;
    }

    public void setFieldBricks(FieldBrick[][] fieldBricks) {
        this.fieldBricks = fieldBricks;
    }

    public FieldWater[][] getFieldWaters() {
        return fieldWaters;
    }

    public void setFieldWaters(FieldWater[][] fieldWaters) {
        this.fieldWaters = fieldWaters;
    }

    public FieldRock[][] getFieldRocks() {
        return fieldRocks;
    }

    public void setFieldRocks(FieldRock[][] fieldRocks) {
        this.fieldRocks = fieldRocks;
    }

    public FieldEagle getFieldEagle() {
        return fieldEagle;
    }

    public void setFieldEagle(FieldEagle fieldEagle) {
        this.fieldEagle = fieldEagle;
    }
}