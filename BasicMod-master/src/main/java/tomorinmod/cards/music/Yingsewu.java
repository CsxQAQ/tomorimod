
package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.YingsewuAction;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Yingsewu extends BaseMusicCard {
    public static final String ID = makeID(Yingsewu.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
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
    }


    private final static int DAMAGE_COMMON = 6;
    private final static int UPG_DAMAGE_COMMON = 3;
    private final static int BLOCK_COMMON = 0;
    private final static int UPG_BLOCK_COMMON = 0;
    private final static int MAGIC_COMMON = 0;
    private final static int UPG_MAGIC_COMMON = 0;

    private final static int DAMAGE_UNCOMMON = 10;
    private final static int UPG_DAMAGE_UNCOMMON = 4;
    private final static int BLOCK_UNCOMMON = 0;
    private final static int UPG_BLOCK_UNCOMMON = 0;
    private final static int MAGIC_UNCOMMON = 0;
    private final static int UPG_MAGIC_UNCOMMON = 0;

    private final static int DAMAGE_RARE = 10;
    private final static int UPG_DAMAGE_RARE = 4;
    private final static int BLOCK_RARE = 0;
    private final static int UPG_BLOCK_RARE = 0;
    private final static int MAGIC_RARE = 0;
    private final static int UPG_MAGIC_RARE = 0;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(musicRarity!=null){
            if(musicRarity.equals(MusicRarity.RARE)){
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToBot(new YingsewuAction());
                addToBot(new YingsewuAction());
                addToBot(new YingsewuAction());
            }else{
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToBot(new YingsewuAction());
            }
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Yingsewu();
    }

}
