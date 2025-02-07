package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.powers.BasePower;

import javax.swing.text.AbstractDocument;

import static tomorimod.TomoriMod.makeID;

public class ConveyFeelingPower extends BasePower {
    public static final String POWER_ID = makeID(ConveyFeelingPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ConveyFeelingPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

}
