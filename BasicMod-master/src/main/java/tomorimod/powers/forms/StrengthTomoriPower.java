package tomorimod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static tomorimod.TomoriMod.makeID;

public class StrengthTomoriPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(StrengthTomoriPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public StrengthTomoriPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
       // this.amount=amount;
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ "层 #y力量 。";
        super.updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        applyEffectPower();
    }


    @Override
    public void applyEffectPower() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, amount), amount));
    }
}
