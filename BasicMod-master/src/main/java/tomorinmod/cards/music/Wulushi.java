package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

public class Wulushi extends BaseMusicCard {
    public static final String ID = makeID(Wulushi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
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
        int tempDamage = damage;
        AbstractMonster target = m;

        if(musicRarity.equals(MusicRarity.RARE)){
            while (tempDamage > 0 && target != null) {
                addToBot(new DamageAction(target, new DamageInfo(p, tempDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

                tempDamage -= 1;
                target = getRandomEnemy(target);
            }
        }else{
            addToBot(new DamageAction(m, new DamageInfo(p, tempDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            target = getRandomEnemy(target);
            if(target!=null){
                addToBot(new DamageAction(target, new DamageInfo(p, tempDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }

    }

    private AbstractMonster getRandomEnemy(AbstractMonster exclude) {
        ArrayList<AbstractMonster> possibleTargets = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDying && !monster.isDead && monster != exclude) {
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
