package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.actions.cardactions.BigGirlsBandEraAction;
import tomorimod.actions.cardactions.SmoothComboAction;
import tomorimod.cards.customcards.SmoothCombo;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class SmoothComboPower extends BasePower {
    public static final String POWER_ID = makeID(SmoothComboPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private int amount;

    public SmoothComboPower(AbstractCreature owner,int amount) {

        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount=amount;
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {

        if(AbstractDungeon.player.hand.contains(usedCard)){
            flash();
            addToBot(new SmoothComboAction(usedCard.type));
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, makeID("SmoothComboPower")));
            }else{
                addToBot(new ReducePowerAction(this.owner, this.owner, makeID("SmoothComboPower"), 1));
            }
        }

    }
}
