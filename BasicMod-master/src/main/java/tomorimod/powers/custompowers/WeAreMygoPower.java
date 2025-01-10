package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.actions.cardactions.SmoothComboAction;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.powers.BasePower;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;

import static tomorimod.TomoriMod.audioPath;
import static tomorimod.TomoriMod.makeID;

public class WeAreMygoPower extends BasePower {
    public static final String POWER_ID = makeID(WeAreMygoPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private boolean isEffected;

    public WeAreMygoPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        isEffected=false;
        updateDescription();
    }

    @Override
    public void updateDescription(){
        if(isEffected){
            description=DESCRIPTIONS[0]+"（ #y已生效 ）";
        }else{
            description=DESCRIPTIONS[0];
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if(!isEffected&&usedCard instanceof BaseMonmentCard){
            flash();
            isEffected=true;
            updateDescription();
            AbstractDungeon.player.increaseMaxHp(5,true);
        }
    }
}
