package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class MutsumiGiveCucumberPower extends BasePower {
    public static final String POWER_ID = makeID(MutsumiGiveCucumberPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;



    public MutsumiGiveCucumberPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    
    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ " ，用 #y一袋黄瓜 将你的抽牌堆卡牌数量补充到 #b" +amount+" 。";
    }

}
