package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import tomorinmod.actions.ApplyGravityAction;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class DarkTomorinPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(DarkTomorinPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public DarkTomorinPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount=amount;
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ "点 #y仪式 。";
        super.updateDescription();
    }

    @Override
    public void onRemove() {
        applyEffectPower();
        super.onRemove();
    }

    @Override
    public void applyEffectPower() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,
                AbstractDungeon.player, new RitualPower(AbstractDungeon.player, amount, true), amount));
    }


}
