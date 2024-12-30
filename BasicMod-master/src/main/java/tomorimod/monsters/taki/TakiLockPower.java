package tomorimod.monsters.taki;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.patches.TakiLockPatch;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class TakiLockPower extends BasePower {
    public static final String POWER_ID = makeID(TakiLockPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public TakiLockPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        TakiLockPatch.AbstractCardLockPatch.isTakiLocked.set(card,true);
    }

}
