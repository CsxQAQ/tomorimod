package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class BaseFormPower extends BasePower {

    public BaseFormPower(String powerID,PowerType type,boolean turnBased,AbstractCreature owner,int amount) {
        super(powerID, type, turnBased, owner, amount);
    }

    @Override
    public void onRemove(){
        if(AbstractDungeon.player.hasRelic(makeID("MicrophoneRelic"))){
            if(this instanceof FormEffect){
                ((FormEffect)this).applyEffectPower();
            }
        }
        //super.onRemove();
    }
}
