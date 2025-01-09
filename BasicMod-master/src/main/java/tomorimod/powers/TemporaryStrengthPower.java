//package tomorimod.powers;
//
//import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//
//import static tomorimod.TomoriMod.makeID;
//
//public class TemporaryStrengthPower extends BasePower {
//    public static final String POWER_ID = makeID(TemporaryStrengthPower.class.getSimpleName());
//    private static final PowerType TYPE = PowerType.BUFF;
//    private static final boolean TURN_BASED = false;
//
//    public TemporaryStrengthPower(AbstractCreature owner, int amount) {
//        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
//        //this.amount = amount;
//        this.loadRegion("strength");
//    }
//
//    public void playApplyPowerSfx() {
//        CardCrawlGame.sound.play("POWER_STRENGTH", 0.05F);
//    }
//
//    @Override
//    public void updateDescription() {
//        description = DESCRIPTIONS[0] + amount + " 点，回合结束时移除。";
//    }
//
//    @Override
//    public void atEndOfTurn(boolean isPlayer) {
//        flash();
//        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
//    }
//
//    @Override
//    public float atDamageGive(float damage, DamageInfo.DamageType type) {
//        return type == DamageInfo.DamageType.NORMAL ? damage + (float)this.amount : damage;
//    }
//}
