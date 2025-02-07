package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.actions.cardactions.BigGirlsBandEraAction;
import tomorimod.actions.cardactions.NewBigGirlsBandEraAction;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class BigGirlsBandEraUpgradedPower extends BasePower {
    public static final String POWER_ID = makeID(BigGirlsBandEraUpgradedPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    //private int amount;
    public BigGirlsBandEraUpgradedPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    @Override
    public void atStartOfTurn() {
        flash();
        for(int i=0;i<amount;i++){
            addToBot(new NewBigGirlsBandEraAction(true));
            //addToBot(new BigGirlsBandEraAction(true));
        }
    }
}
