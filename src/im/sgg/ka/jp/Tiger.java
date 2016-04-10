package im.sgg.ka.jp;

import java.awt.*;

/**
 * Created by sergiy on 10.03.16.
 * Java Developer lessons
 * kademika.com
 */
public class Tiger extends Aggressor {
    private int armor;


    public Tiger(ActionField af, BattleField bf) throws InterruptedException {
        this(af, bf,1);
    }

    public Tiger(ActionField af, BattleField bf, int armor) throws InterruptedException {
        super(af, bf);
        this.armor = armor;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
