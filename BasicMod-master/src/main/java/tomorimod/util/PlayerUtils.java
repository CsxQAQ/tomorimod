package tomorimod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static tomorimod.TomoriMod.makeID;

public class PlayerUtils {

    public static int getPowerNum(String powerId){
        if(AbstractDungeon.player.hasPower(makeID(powerId))){
            return AbstractDungeon.player.getPower(makeID(powerId)).amount;
        }
        return 0;
    }

}
