package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class FriendlyMonsterPower extends BasePower {
    public static final String POWER_ID = makeID(FriendlyMonsterPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public FriendlyMonsterPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.owner == AbstractDungeon.player){
            return 0;
        }
        return damageAmount;
    }

//    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        if(power.type.equals(PowerType.DEBUFF)&&source==AbstractDungeon.player){
//            addToBot(new RemoveSpecificPowerAction());
//        }
//    }
}