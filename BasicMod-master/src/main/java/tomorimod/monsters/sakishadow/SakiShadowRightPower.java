package tomorimod.monsters.sakishadow;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class SakiShadowRightPower extends BasePower {
    public static final String POWER_ID = makeID(SakiShadowRightPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SakiShadowRightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void decreaseMaxHealth(){
        AbstractDungeon.player.maxHealth-=this.amount;
    }


//                    for(
//    AbstractPower power :this.powers){
//        if(power instanceof SakiRightPower){
//            ((SakiRightPower)power).decreaseMaxHealth();
//            break;
//        }
//    }
}
