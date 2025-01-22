//package tomorimod.monsters.taki;
//
//import com.megacrit.cardcrawl.actions.utility.UseCardAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import tomorimod.monsters.mutsumioperator.TakiPressurePatch;
//import tomorimod.powers.BasePower;
//
//import static tomorimod.TomoriMod.makeID;
//
//public class TakiPressurePower extends BasePower {
//    public static final String POWER_ID = makeID(TakiPressurePower.class.getSimpleName());
//    private static final PowerType TYPE = PowerType.BUFF;
//    private static final boolean TURN_BASED = false;
//
//
//    public TakiPressurePower(AbstractCreature owner) {
//        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
//    }
//
//    @Override
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.set(card,true);
//    }
//
//}
