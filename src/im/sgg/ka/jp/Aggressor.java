package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 19.03.16.
 * Java Developer lessons
 * kademika.com
 */
public class Aggressor extends Tank {
    public static final String [] START_COORDS = {"4_2","8_2","8_4"};

    public Aggressor(ActionField af, BattleField bf) {
        super(af, bf);
        int index = intRandom(0,START_COORDS.length-1);
        this.setQuadrantXY(
                Integer.parseInt(START_COORDS[index].substring(0,1)),
                Integer.parseInt(START_COORDS[index].substring(2,3))
        );
//        bf.updateQuadrant(
//                Integer.parseInt(START_COORDS[index].substring(2,3)),
//                Integer.parseInt(START_COORDS[index].substring(0,1)),
//                "");
//        bf.printField();
    }
}
