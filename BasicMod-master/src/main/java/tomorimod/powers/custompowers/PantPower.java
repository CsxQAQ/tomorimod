package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.powers.BasePower;
import tomorimod.powers.forms.BaseFormPower;
import tomorimod.powers.forms.FormEffect;

import static tomorimod.TomoriMod.makeID;

public class PantPower extends BasePower{
    public static final String POWER_ID = makeID(PantPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public PantPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+" 点生命值。";
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        flash();
        addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player,amount));
    }


}
