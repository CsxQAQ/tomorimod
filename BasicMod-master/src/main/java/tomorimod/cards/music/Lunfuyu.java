package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.utils.MusicDamageAllEnemiesAction;
import tomorimod.monitors.card.LunfuyuMonitor;
import tomorimod.util.CardStats;

public class Lunfuyu extends BaseMusicCard {
    public static final String ID = makeID(Lunfuyu.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            2
    );

    public Lunfuyu() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
        isMultiDamage=true;
    }


    public final static int DAMAGE_COMMON = 0;
    public final static int UPG_DAMAGE_COMMON = 0;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 6;
    public final static int UPG_MAGIC_COMMON = 3;

    public final static int DAMAGE_UNCOMMON = 0;
    public final static int UPG_DAMAGE_UNCOMMON = 0;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 9;
    public final static int UPG_MAGIC_UNCOMMON = 4;

    public final static int DAMAGE_RARE = 0;
    public final static int UPG_DAMAGE_RARE = 0;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 9;
    public final static int UPG_MAGIC_RARE = 4;

    //private int curHpIncreaseNum=-1;
    private int curHpChangeNum=-1;
    private int curHpChangeWholeBattleNum=-1;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateBaseDamage();
        addToBot(new MusicDamageAllEnemiesAction(p, baseDamage, this.damageTypeForTurn,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
        switch (musicRarity) {
            case COMMON:
            case DEFAULT:
            case UNCOMMON:
                baseDamage=LunfuyuMonitor.hpChangeNum*magicNumber;
                break;
            case RARE:
                baseDamage=LunfuyuMonitor.hpChangeNumWholeBattle*magicNumber;
                break;
        }
    }

    @Override
    public void update(){
        super.update();
        if(CardCrawlGame.mode!= CardCrawlGame.GameMode.CHAR_SELECT){
            if(curHpChangeNum!=LunfuyuMonitor.hpChangeNum||curHpChangeWholeBattleNum!=LunfuyuMonitor.hpChangeNumWholeBattle){
                calculateBaseDamage();
                applyPowers();
                updateDescription();
                curHpChangeNum=LunfuyuMonitor.hpChangeNum; //避免频繁applyPower
                curHpChangeWholeBattleNum=LunfuyuMonitor.hpChangeNumWholeBattle;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lunfuyu();
    }

}
