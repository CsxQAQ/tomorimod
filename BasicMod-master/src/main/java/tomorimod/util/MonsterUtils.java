package tomorimod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static tomorimod.TomoriMod.makeID;

public class MonsterUtils {

    public static boolean hasPower(String monsterId,String powerId){
        for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.id.equals(makeID(monsterId))){
                if(monster.hasPower(makeID(powerId))){
                    return true;
                }
            }
        }
        return false;
    }

    public static int getPowerNum(String monsterId,String powerId){
        for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.id.equals(makeID(monsterId))){
                if(monster.hasPower(makeID(powerId))){
                    return monster.getPower(makeID(powerId)).amount;
                }else{
                    return 0;
                }
            }
        }
        return 0;
    }
}
