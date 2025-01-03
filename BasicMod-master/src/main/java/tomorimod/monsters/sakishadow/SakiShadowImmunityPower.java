package tomorimod.monsters.sakishadow;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class SakiShadowImmunityPower extends BasePower {
    public static final String POWER_ID = makeID(SakiShadowImmunityPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SakiShadowImmunityPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info instanceof MusicDamageInfo){
            return damageAmount;
        }
        return 0;
//        if(info.type.equals(CustomTags.MUSIC)){
//            return damageAmount;
//        }
//        return 0;
    }


}
