package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class BehindAttackPower extends BasePower {
    public static final String POWER_ID = makeID(BehindAttackPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public BehindAttackPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        if(owner== AbstractDungeon.player){
            loadRegion("backAttack");
        }else{
            loadRegion("backAttack2");
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage*1.5f;
    }

//    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
//        return damage;
//    }



}
