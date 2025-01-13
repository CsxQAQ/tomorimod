package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorimod.powers.BasePower;
import tomorimod.powers.forms.BaseFormPower;
import tomorimod.powers.forms.FormEffect;

import static tomorimod.TomoriMod.makeID;

public class StrengthTomoriPower extends BasePower {
    public static final String POWER_ID = makeID(StrengthTomoriPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public StrengthTomoriPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ " 层 #y力量 。";
        super.updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, amount), amount));
    }

}
