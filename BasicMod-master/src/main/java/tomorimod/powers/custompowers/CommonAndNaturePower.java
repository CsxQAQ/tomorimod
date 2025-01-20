package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class CommonAndNaturePower extends BasePower {
    public static final String POWER_ID = makeID(CommonAndNaturePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public CommonAndNaturePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if(card instanceof BaseMusicCard){
            if(((BaseMusicCard) card).musicRarity.equals(BaseMusicCard.MusicRarity.COMMON)){
                return 3.0f*damage;
            }
        }
        return damage;
    }

//    @Override
//    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
//        if(info instanceof MusicDamageInfo){
//            MusicDamageInfo musicDamageInfo=(MusicDamageInfo)info;
//            if(musicDamageInfo.rarity.equals(BaseMusicCard.MusicRarity.COMMON)){
//                return 3*damageAmount;
//            }
//        }
//        return damageAmount;
//    }

}
