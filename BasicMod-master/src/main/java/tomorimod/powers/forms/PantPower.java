package tomorimod.powers.forms;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static tomorimod.TomoriMod.makeID;

public class PantPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(PantPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public PantPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        //this.amount=amount;
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+"层 #y格挡 。";
        super.updateDescription();
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        applyEffectPower();
    }

    @Override
    public void applyEffectPower() {
        flash();
        addToTop(new GainBlockAction(AbstractDungeon.player,amount));
    }


}
