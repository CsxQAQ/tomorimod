package tomorimod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static tomorimod.TomoriMod.makeID;

public class ImmunityPower extends BasePower {
    public static final String POWER_ID = makeID(ImmunityPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ImmunityPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

//    @Override
//    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
//        if (damage > 1.0F) {
//            damage = 0;
//        }
//        return damage;
//    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfRound() {
        flash();
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, makeID("ImmunityPower")));
        } else if(this.amount==1) {
            if(!AbstractDungeon.player.hasPower(makeID("ShinePower"))){
                addToBot(new ReducePowerAction(this.owner, this.owner, makeID("ImmunityPower"), 1));
            }else if(AbstractDungeon.player.getPower(makeID("ShinePower")).amount<5){
                addToBot(new ReducePowerAction(this.owner, this.owner, makeID("ImmunityPower"), 1));
            }else{
                addToBot(new ReducePowerAction(this.owner, this.owner, makeID("ShinePower"), 5));
            }
        }else{
            addToBot(new ReducePowerAction(this.owner, this.owner, makeID("ImmunityPower"), 1));
        }
    }
}
