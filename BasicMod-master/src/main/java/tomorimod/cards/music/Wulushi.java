package tomorimod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.WulushiAction;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.cards.notshow.utilcards.TestCard;
import tomorimod.util.CardStats;

import java.util.ArrayList;

public class Wulushi extends BaseMusicCard {
    public static final String ID = makeID(Wulushi.class.getSimpleName());
    public static final CardStats info = new CardStats(

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


    public final static int DAMAGE_COMMON = 10;
    public final static int UPG_DAMAGE_COMMON = 4;
    public final static int BLOCK_COMMON = 0;
    public final static int UPG_BLOCK_COMMON = 0;
    public final static int MAGIC_COMMON = 0;
    public final static int UPG_MAGIC_COMMON = 0;

    public final static int DAMAGE_UNCOMMON = 12;
    public final static int UPG_DAMAGE_UNCOMMON = 5;
    public final static int BLOCK_UNCOMMON = 0;
    public final static int UPG_BLOCK_UNCOMMON = 0;
    public final static int MAGIC_UNCOMMON = 0;
    public final static int UPG_MAGIC_UNCOMMON = 0;

    public final static int DAMAGE_RARE = 12;
    public final static int UPG_DAMAGE_RARE = 5;
    public final static int BLOCK_RARE = 0;
    public final static int UPG_BLOCK_RARE = 0;
    public final static int MAGIC_RARE = 0;
    public final static int UPG_MAGIC_RARE = 0;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if(musicRarity!=null){
            if(musicRarity.equals(MusicRarity.RARE)){
                addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new WulushiAction(baseDamage-1,m));
            }else{
                addToBot(new DamageAction(m, new MusicDamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractMonster target=getRandomEnemy(m);
                        TestCard testCard=new TestCard();
                        testCard.baseDamage=Wulushi.this.baseDamage;
                        testCard.calculateCardDamage(target);
                        addToBot(new DamageAction(target, new MusicDamageInfo(p, testCard.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                        isDone=true;
                    }
                });
            }
        }
    }

    public AbstractMonster getRandomEnemy(AbstractMonster except) {
        ArrayList<AbstractMonster> possibleTargets = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
//            if (monster!=except&&!monster.isDying && !monster.isDeadOrEscaped()&&!monster.hasPower(makeID("FriendlyMonsterPower"))) {
//                possibleTargets.add(monster);
//            }
            if (monster!=except&&!monster.isDying && !monster.isDeadOrEscaped()) {
                possibleTargets.add(monster);
            }
        }
        if (possibleTargets.isEmpty()) {
            return null;
        }
        return possibleTargets.get(AbstractDungeon.miscRng.random(possibleTargets.size() - 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wulushi();
    }

}
