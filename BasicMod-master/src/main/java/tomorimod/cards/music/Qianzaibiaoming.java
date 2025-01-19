package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.music.utils.MusicDamageAllEnemiesAction;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.monitors.card.LunfuyuMonitor;
import tomorimod.powers.GravityPower;
import tomorimod.util.CardStats;
import tomorimod.util.PlayerUtils;

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

    public final static int DAMAGE_COMMON = 0;
    public final static int UPG_DAMAGE_COMMON = 0;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 1;
    public final static int UPG_MAGIC_COMMON = 1;

    public final static int DAMAGE_UNCOMMON = 0;
    public final static int UPG_DAMAGE_UNCOMMON = 0;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 2;
    public final static int UPG_MAGIC_UNCOMMON = 1;

    public final static int DAMAGE_RARE = 0;
    public final static int UPG_DAMAGE_RARE = 0;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 2;
    public final static int UPG_MAGIC_RARE = 1;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateBaseDamage();
        if(musicRarity.equals(MusicRarity.RARE)){
            addToBot(new MusicDamageAllEnemiesAction(p, baseDamage,
                    this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }else{
            calculateCardDamage(m);
            addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, this.damageType),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }


    public void updateDescriptionWithUPG(){
        getBaseDescription();
        this.rawDescription+=cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers(){
        calculateBaseDamage();
        super.applyPowers();
        updateDescriptionWithUPG();
    }

    @Override
    public void onMoveToDiscard() {
        getBaseDescription();
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        updateDescriptionWithUPG();
    }

    public void calculateBaseDamage(){
        baseDamage=PlayerUtils.getPowerNum(makeID("GravityPower"))*magicNumber;
    }


    @Override
    public Qianzaibiaoming makeStatEquivalentCopy(){
        BaseMusicCard card= super.makeStatEquivalentCopy();
        Qianzaibiaoming qianzaibiaoming=(Qianzaibiaoming) card;
        if(musicRarity.equals(MusicRarity.RARE)){
            target=CardTarget.ALL_ENEMY;
            isMultiDamage=true;
        }
        return qianzaibiaoming;
    }

    @Override
    public void setMusicRarity(MusicRarity musicRarity){
        super.setMusicRarity(musicRarity);
        if(musicRarity.equals(MusicRarity.RARE)){
            target=CardTarget.ALL_ENEMY;
            isMultiDamage=true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Qianzaibiaoming();
    }

}
