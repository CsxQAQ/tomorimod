package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.util.CardStats;

import java.util.ArrayList;

public class Wulushi extends BaseMusicCard {
    public static final String ID = makeID(Wulushi.class.getSimpleName());
    private static final CardStats info = new CardStats(

            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public Wulushi() {
        super(ID, info, new NumsInfo(
                DAMAGE_COMMON, UPG_DAMAGE_COMMON, DAMAGE_UNCOMMON, UPG_DAMAGE_UNCOMMON, DAMAGE_RARE, UPG_DAMAGE_RARE,
                BLOCK_COMMON, UPG_BLOCK_COMMON, BLOCK_UNCOMMON, UPG_BLOCK_UNCOMMON, BLOCK_RARE, UPG_BLOCK_RARE,
                MAGIC_COMMON, UPG_MAGIC_COMMON, MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON, MAGIC_RARE, UPG_MAGIC_RARE
        ));
    }


    private final static int DAMAGE_COMMON = 10;
    private final static int UPG_DAMAGE_COMMON = 4;
    private final static int BLOCK_COMMON = 0;
    private final static int UPG_BLOCK_COMMON = 0;
    private final static int MAGIC_COMMON = 0;
    private final static int UPG_MAGIC_COMMON = 0;

    private final static int DAMAGE_UNCOMMON = 12;
    private final static int UPG_DAMAGE_UNCOMMON = 5;
    private final static int BLOCK_UNCOMMON = 0;
    private final static int UPG_BLOCK_UNCOMMON = 0;
    private final static int MAGIC_UNCOMMON = 0;
    private final static int UPG_MAGIC_UNCOMMON = 0;

    private final static int DAMAGE_RARE = 12;
    private final static int UPG_DAMAGE_RARE = 5;
    private final static int BLOCK_RARE = 0;
    private final static int UPG_BLOCK_RARE = 0;
    private final static int MAGIC_RARE = 0;
    private final static int UPG_MAGIC_RARE = 0;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int reduceDamage = 0;
        if(musicRarity!=null){
            if(musicRarity.equals(MusicRarity.RARE)){
                addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                while (reduceDamage < baseDamage) {
                    BaseMusicCard wulushi=this.makeStatEquivalentCopy();
                    wulushi.baseDamage=wulushi.baseDamage-reduceDamage;
                    if(wulushi.baseDamage>0){
                        addToBot(new AttackDamageRandomEnemyAction(wulushi,AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    }
                    reduceDamage++;
                }
            }else{
                addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new AttackDamageRandomEnemyAction(this,AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wulushi();
    }

}
