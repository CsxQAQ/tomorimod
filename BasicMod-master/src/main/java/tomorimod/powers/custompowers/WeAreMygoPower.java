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

import static tomorimod.TomoriMod.makeID;

public class WeAreMygoPower extends BasePower {
    public static final String POWER_ID = makeID(WeAreMygoPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public WeAreMygoPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if(usedCard instanceof BaseMonmentCard){
            flash();
            AbstractDungeon.player.increaseMaxHp(5,true);
        }
    }
}
