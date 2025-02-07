
package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.cards.music.utils.MusicDamageAllEnemiesAction;
import tomorimod.util.CardStats;
import tomorimod.util.PlayerUtils;

public class Yinyihui extends BaseMusicCard {
    public static final String ID = makeID(Yinyihui.class.getSimpleName());
    public static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            2
    );

    public Yinyihui() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
        isMultiDamage=true;
    }


    public final static int DAMAGE_COMMON = 0;
    public final static int UPG_DAMAGE_COMMON = 0;
    public final static int BLOCK_COMMON = 10;
    public final static int UPG_BLOCK_COMMON = 4;
    public final static int MAGIC_COMMON = 0;
    public final static int UPG_MAGIC_COMMON = 0;

    public final static int DAMAGE_UNCOMMON = 0;
    public final static int UPG_DAMAGE_UNCOMMON = 0;
    public final static int BLOCK_UNCOMMON = 14;
    public final static int UPG_BLOCK_UNCOMMON = 5;
    public final static int MAGIC_UNCOMMON = 0;
    public final static int UPG_MAGIC_UNCOMMON = 0;

    public final static int DAMAGE_RARE = 0;
    public final static int UPG_DAMAGE_RARE = 0;
    public final static int BLOCK_RARE = 14;
    public final static int UPG_BLOCK_RARE = 5;
    public final static int MAGIC_RARE = 0;
    public final static int UPG_MAGIC_RARE = 0;

    //private int curBlock=-1;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if(musicRarity.equals(MusicRarity.RARE)){
            addToBot(new GainBlockAction(p, block));

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    baseDamage = AbstractDungeon.player.currentBlock;
                    addToTop(new MusicDamageAllEnemiesAction(p, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    this.isDone = true;
                }
            });
        }else{
            addToBot(new GainBlockAction(p,block));
            addToBot(new MusicDamageAllEnemiesAction(p, block, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
        if(musicRarity.equals(MusicRarity.RARE)){
            baseDamage= AbstractDungeon.player.currentBlock+block;
        }else{
            baseDamage=block;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yinyihui();
    }

}
