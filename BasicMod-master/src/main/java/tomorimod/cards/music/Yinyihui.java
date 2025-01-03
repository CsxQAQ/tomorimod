
package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.utils.MusicDamageAllEnemiesAction;
import tomorimod.util.CardStats;

public class Yinyihui extends BaseMusicCard {
    public static final String ID = makeID(Yinyihui.class.getSimpleName());
    private static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    public Yinyihui() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
        isMultiDamage=true;
    }


    private final static int DAMAGE_COMMON = 0;
    private final static int UPG_DAMAGE_COMMON = 0;
    private final static int BLOCK_COMMON = 8;
    private final static int UPG_BLOCK_COMMON = 4;
    private final static int MAGIC_COMMON = 0;
    private final static int UPG_MAGIC_COMMON = 0;

    private final static int DAMAGE_UNCOMMON = 0;
    private final static int UPG_DAMAGE_UNCOMMON = 0;
    private final static int BLOCK_UNCOMMON = 12;
    private final static int UPG_BLOCK_UNCOMMON = 5;
    private final static int MAGIC_UNCOMMON = 0;
    private final static int UPG_MAGIC_UNCOMMON = 0;

    private final static int DAMAGE_RARE = 0;
    private final static int UPG_DAMAGE_RARE = 0;
    private final static int BLOCK_RARE = 12;
    private final static int UPG_BLOCK_RARE = 5;
    private final static int MAGIC_RARE = 0;
    private final static int UPG_MAGIC_RARE = 0;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(musicRarity!=null){
            if(musicRarity.equals(MusicRarity.RARE)){
                addToBot(new GainBlockAction(p, block));

                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        int curBlock = AbstractDungeon.player.currentBlock;
                        addToBot(new MusicDamageAllEnemiesAction(p, curBlock, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                        this.isDone = true;
                    }
                });
            }else{
                addToBot(new GainBlockAction(p,block));
                addToBot(new MusicDamageAllEnemiesAction(p, block, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yinyihui();
    }

}
