
package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import tomorimod.actions.cardactions.YingsewuAction;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.util.CardStats;

public class Yingsewu extends BaseMusicCard {
    public static final String ID = makeID(Yingsewu.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Yingsewu() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));

        exhaust=true;
    }


    public final static int DAMAGE_COMMON = 4;
    public final static int UPG_DAMAGE_COMMON = 2;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 1;
    public final static int UPG_MAGIC_COMMON = 0;

    public final static int DAMAGE_UNCOMMON = 6;
    public final static int UPG_DAMAGE_UNCOMMON = 3;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 1;
    public final static int UPG_MAGIC_UNCOMMON = 0;

    public final static int DAMAGE_RARE = 6;
    public final static int UPG_DAMAGE_RARE = 3;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 1;
    public final static int UPG_MAGIC_RARE = 0;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(p,p,new IntangiblePlayerPower(p,magicNumber),magicNumber));
    }

    @Override
    public void triggerAfterCopy(){
        if(musicRarity.equals(MusicRarity.RARE)){
            exhaust=false;
        }else{
            exhaust=true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yingsewu();
    }

}
