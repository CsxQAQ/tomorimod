package tomorimod.monsters.sakishadow;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.cards.music.BaseMusicCard;
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
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type, AbstractCard card){
        if(!(card instanceof BaseMusicCard)){
            return 0;
        }
        return damage;
    }
}
