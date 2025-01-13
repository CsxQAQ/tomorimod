package tomorimod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.powers.forms.BaseFormPower;
import tomorimod.powers.forms.FormEffect;

import static tomorimod.TomoriMod.makeID;

public class PlayerUtils {

    public static int getPowerNum(String powerId){
        if(AbstractDungeon.player.hasPower(powerId)){
            return AbstractDungeon.player.getPower(powerId).amount;
        }
        return 0;
    }

    public static FormEffect getFormPower(String powerId){
        if(AbstractDungeon.player.hasPower(powerId)){
            AbstractPower power=AbstractDungeon.player.getPower(powerId);
            if(power instanceof FormEffect){
                return (FormEffect)power;
            }
        }
        return null;
    }

}
