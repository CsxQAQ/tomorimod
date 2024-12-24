package tomorinmod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class CompetePowerMonster extends BasePower {
    public static final String POWER_ID = makeID(CompetePowerMonster.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private int amount;

    public CompetePowerMonster(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount=amount;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage * 2.0f;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return damage * 2.0f;
    }

    @Override
    public void atEndOfRound() {
        flash();
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, makeID("CompeteMonsterPower")));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, makeID("CompeteMonsterPower"), 1));
        }
    }

    @Override
    public void onDeath() {
        if(AbstractDungeon.player.hasPower(makeID("CompetePower"))){
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,makeID("CompetePower")));
        }
    }
}
