package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;

import static tomorimod.TomoriMod.makeID;

public class ShineWithMePower extends BasePower {
    public static final String POWER_ID = makeID(ShineWithMePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public ShineWithMePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        this.updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+"（当前数量："+ SaveMusicDiscoverd.getInstance().musicDiscoveredNum+"）";
    }

}
