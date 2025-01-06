package tomorimod.monsters.taki;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class TakiProtectPower extends BasePower {
    public static final String POWER_ID = makeID(TakiProtectPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public TakiProtectPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        loadRegion("dexterity");
    }

}
