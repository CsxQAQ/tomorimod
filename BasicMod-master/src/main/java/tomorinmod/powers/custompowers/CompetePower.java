package tomorinmod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class CompetePower extends BasePower {
    public static final String POWER_ID = makeID(CompetePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private int amount;

    public CompetePower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount=amount;
    }

    @Override
    public void atEndOfRound() {
        flash();
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, makeID("CompetePower")));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, makeID("CompetePower"), 1));
        }
    }
}
