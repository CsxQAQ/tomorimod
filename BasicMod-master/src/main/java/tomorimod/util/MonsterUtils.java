package tomorimod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static tomorimod.TomoriMod.makeID;

public class MonsterUtils {

    public static AbstractMonster getMonster(String monsterId){
        for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.id.equals(makeID(monsterId))){
                return monster;
            }
        }
        return null;
    }

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

    public static int getPowerNum(AbstractMonster monster,String powerId){
        if(monster.hasPower(makeID(powerId))){
            return monster.getPower(makeID(powerId)).amount;
        }else{
            return 0;
        }
    }
}
