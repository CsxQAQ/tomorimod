package tomorimod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;


public class MonsterUtils {

    public static AbstractMonster getMonster(String monsterId){
        for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.id.equals(monsterId)){
                return monster;
            }
        }
        return null;
    }

    public static boolean hasPower(String monsterId,String powerId){
        for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.id.equals(monsterId)){
                if(monster.hasPower(powerId)){
                    return true;
                }
            }
        }
        return false;
    }

    public static int getPowerNum(String monsterId,String powerId){
        for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.id.equals(monsterId)){
                if(monster.hasPower(powerId)){
                    return monster.getPower(powerId).amount;
                }else{
                    return 0;
                }
            }
        }
        return 0;
    }

    public static int getPowerNum(AbstractMonster monster,String powerId){
        if(monster.hasPower(powerId)){
            return monster.getPower(powerId).amount;
        }else{
            return 0;
        }
    }

    public static AbstractMonster getRandomEnemy(AbstractMonster except) {
        ArrayList<AbstractMonster> possibleTargets = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (monster!=except&&!monster.isDying && !monster.isDeadOrEscaped()) {
                possibleTargets.add(monster);
            }
        }
        if (possibleTargets.isEmpty()) {
            return null;
        }
        return possibleTargets.get(AbstractDungeon.miscRng.random(possibleTargets.size() - 1));
    }

    public static int getDebuffNum(AbstractMonster monster){
        int negativeEffectsCount = 0;
        for (AbstractPower power : monster.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF) {
                negativeEffectsCount += power.amount;
            }
        }
        return negativeEffectsCount;
    }
}
