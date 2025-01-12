package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
    public final static int MAGIC_COMMON = 5;
    public final static int UPG_MAGIC_COMMON = 3;

    public final static int DAMAGE_UNCOMMON = 0;
    public final static int UPG_DAMAGE_UNCOMMON = 0;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 8;
    public final static int UPG_MAGIC_UNCOMMON = 4;

    public final static int DAMAGE_RARE = 0;
    public final static int UPG_DAMAGE_RARE = 0;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 8;
    public final static int UPG_MAGIC_RARE = 4;

    private int curGravityNum=-1;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateBaseDamage();
        if(musicRarity.equals(MusicRarity.RARE)){
            addToBot(new MusicDamageAllEnemiesAction(p, baseDamage,
                    this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }else{
            calculateCardDamage(m);
            addToBot(new DamageAction(m, new DamageInfo(p, damage, this.damageType),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void updateDescription(){
        switch (musicRarity) {
            case COMMON:
            case DEFAULT:
            case UNCOMMON:
                this.rawDescription=cardStrings.DESCRIPTION;
                break;
            case RARE:
                this.rawDescription=cardStrings.EXTENDED_DESCRIPTION[0];
                break;
        }
        if(CardCrawlGame.mode!= CardCrawlGame.GameMode.CHAR_SELECT){
            this.rawDescription+=cardStrings.UPGRADE_DESCRIPTION;
        }
        initializeDescription();
    }

    public void calculateBaseDamage(){
        baseDamage= PlayerUtils.getPowerNum(makeID("GravityPower"))*magicNumber;
    }

    @Override
    public void update(){
        super.update();
        if(musicRarity.equals(MusicRarity.RARE)){
            target=CardTarget.ALL_ENEMY;
        }
        if(CardCrawlGame.mode!= CardCrawlGame.GameMode.CHAR_SELECT){
            if(curGravityNum!=PlayerUtils.getPowerNum(makeID("GravityPower"))){
                calculateBaseDamage();
                applyPowers();
                updateDescription();
                curGravityNum=PlayerUtils.getPowerNum(makeID("GravityPower"));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Qianzaibiaoming();
    }

}
