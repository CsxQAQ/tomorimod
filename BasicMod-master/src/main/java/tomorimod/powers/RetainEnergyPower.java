package tomorimod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static tomorimod.TomoriMod.makeID;

public class RetainEnergyPower extends BasePower {
    public static final String POWER_ID = makeID(RetainEnergyPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public RetainEnergyPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner,0);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            flash();
            amount2= EnergyPanel.totalCount;
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new GainEnergyAction(this.amount2));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, makeID("RetainEnergyPower")));
    }
}
