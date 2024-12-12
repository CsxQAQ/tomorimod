package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.BitianbanzouAction;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

public class Wulushi extends BaseMusic {
    public static final String ID = makeID(Wulushi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Wulushi() {
        super(ID, info);
        this.musicUpgradeDamage=UPG_DAMAGE;
        //this.musicUpgradeMagicNumber=UPG_MAGIC;
        this.setDamage(DAMAGE,UPG_DAMAGE);
        //this.setMagic(MAGIC,UPG_MAGIC);
        this.exhaust=true;
    }


    private final static int DAMAGE=10;
    private final static int UPG_DAMAGE=4;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int tempDamage = damage; // 初始化临时伤害值
        AbstractMonster target = m; // 首个目标是用户指定的敌人

        while (tempDamage > 0 && target != null) {
            // 对当前目标造成伤害
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(target, new DamageInfo(p, tempDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL)
            );

            tempDamage -= 1; // 临时伤害值减少
            target = getRandomEnemy(target); // 获取另一个随机敌人
        }
    }

    // 获取一个与当前目标不同的随机敌人
    private AbstractMonster getRandomEnemy(AbstractMonster exclude) {
        ArrayList<AbstractMonster> possibleTargets = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDying && !monster.isDead && monster != exclude) {
                possibleTargets.add(monster);
            }
        }

        if (possibleTargets.isEmpty()) {
            return null; // 如果没有可选目标，返回 null
        }

        return possibleTargets.get(AbstractDungeon.miscRng.random(possibleTargets.size() - 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wulushi();
    }

}
