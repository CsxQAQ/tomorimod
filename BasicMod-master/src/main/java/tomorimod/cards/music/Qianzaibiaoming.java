package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.utils.MusicDamageAllEnemiesAction;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.powers.GravityPower;
import tomorimod.util.CardStats;

public class Qianzaibiaoming extends BaseMusicCard {
    public static final String ID = makeID(Qianzaibiaoming.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Qianzaibiaoming() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }

    public final static int DAMAGE_COMMON = 5;
    public final static int UPG_DAMAGE_COMMON = 3;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 2;
    public final static int UPG_MAGIC_COMMON = 1;

    public final static int DAMAGE_UNCOMMON = 8;
    public final static int UPG_DAMAGE_UNCOMMON = 4;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 3;
    public final static int UPG_MAGIC_UNCOMMON = 1;

    public final static int DAMAGE_RARE = 8;
    public final static int UPG_DAMAGE_RARE = 4;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 3;
    public final static int UPG_MAGIC_RARE = 1;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int gravityAmount=0;
        if(AbstractDungeon.player!=null){
            if(AbstractDungeon.player.getPower(GravityPower.POWER_ID)!=null){
                gravityAmount=AbstractDungeon.player.getPower(GravityPower.POWER_ID).amount;
            }
        }
        if(musicRarity!=null){
            if(musicRarity.equals(MusicRarity.RARE)) {

                addToBot(new MusicDamageAllEnemiesAction(p, baseDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new MusicDamageAllEnemiesAction(p, gravityAmount*magicNumber, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

            }else{
                int tempBaseDamage=baseDamage;
                addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                baseDamage=gravityAmount*magicNumber;
                calculateCardDamage(m);
                addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                baseDamage=tempBaseDamage;
            }
        }
    }

    @Override
    public void update(){
        super.update();
        if(musicRarity!=null){
            this.target=CardTarget.ALL_ENEMY;
        }
        updateDescription();
    }

    @Override
    public void updateDescription(){
        if (musicRarity != null) {
            int gravityAmount=0;
            if(AbstractDungeon.player!=null){
                if(AbstractDungeon.player.getPower(GravityPower.POWER_ID)!=null){
                    gravityAmount=AbstractDungeon.player.getPower(GravityPower.POWER_ID).amount;
                }
            }
            switch (musicRarity) {
                case COMMON:
                case DEFAULT:
                case UNCOMMON:
                    this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION
                            +"（拥有"+ gravityAmount+"点重力）";
                    break;
                case RARE:
                    this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0]
                            +"（拥有"+gravityAmount+"点重力）";
                    break;
            }
        }
        initializeDescription();
    }
    @Override
    public AbstractCard makeCopy() {
        return new Qianzaibiaoming();
    }

}
