package tomorimod.monsters;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class BaseMonster extends CustomMonster {

    public boolean isPlayBGM=false;

    public BaseMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    public abstract void takeTurn();

    protected abstract void getMove(int i);
}
