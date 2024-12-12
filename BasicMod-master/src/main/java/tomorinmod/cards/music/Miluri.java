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

public class Miluri extends BaseMusic {
    public static final String ID = makeID(Miluri.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    public Miluri() {
        super(ID, info);

        this.musicUpgradeDamage=UPG_DAMAGE;
        this.setDamage(DAMAGE,UPG_DAMAGE);
    }

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shineAmount = p.getPower(makeID("Shine")).amount; // 获取闪耀值

        for (int i = 0; i < shineAmount + 1; i++) {
            // 获取一个随机敌人
            AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            if (target != null) {
                // 对随机目标造成伤害
                addToBot(new DamageAction(target, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Miluri();
    }

}
