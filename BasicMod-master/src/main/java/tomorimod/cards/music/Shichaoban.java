package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.util.CardStats;

public class Shichaoban extends BaseMusicCard {
    public static final String ID = makeID(Shichaoban.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Shichaoban() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }


    public final static int DAMAGE_COMMON = 4;
    public final static int UPG_DAMAGE_COMMON = 2;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 6;
    public final static int UPG_MAGIC_COMMON = 3;

    public final static int DAMAGE_UNCOMMON = 6;
    public final static int UPG_DAMAGE_UNCOMMON = 3;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 9;
    public final static int UPG_MAGIC_UNCOMMON = 5;

    public final static int DAMAGE_RARE = 6;
    public final static int UPG_DAMAGE_RARE = 3;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 0;
    public final static int UPG_MAGIC_RARE = 0;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if(musicRarity.equals(MusicRarity.RARE)){
            baseDamage=baseDamage*2;
        }else{
            baseDamage=baseDamage+magicNumber;
        }
        updateDescription();
    }

    @Override
    public void updateDescription(){
        if (musicRarity != null) {
            switch (musicRarity) {
                case COMMON:
                case DEFAULT:
                case UNCOMMON:
                    this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION
                            +"（当前基础伤害："+baseDamage+ "）";
                    break;
                case RARE:
                    this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0]
                            +"（当前基础伤害："+baseDamage+ "）";
                    break;
            }
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shichaoban();
    }
}
