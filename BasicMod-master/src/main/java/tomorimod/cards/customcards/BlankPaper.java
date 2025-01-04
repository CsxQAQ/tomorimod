package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.special.BlankPaperAttack;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

import java.lang.reflect.Field;

public class BlankPaper extends BaseCard {

    public static final String ID = makeID(BlankPaper.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public BlankPaper() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && (m.intent == AbstractMonster.Intent.ATTACK ||
                m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            int monsterDamage = getPublicField(m, "intentDmg", Integer.class);
            int attackCount = getPublicField(m, "intentMultiAmt", Integer.class);

            AbstractCard attackCard = new BlankPaperAttack();
            attackCard.baseDamage = monsterDamage;
            attackCard.baseMagicNumber = attackCount>0?attackCount:1;
            //attackCard.magicNumber=attackCount>0?attackCount:1;

            addToBot(new MakeTempCardInHandAction(attackCard));
        }else{
            addToBot(new TextAboveCreatureAction(m, "无效的目标！"));
        }

    }

    public static <T> T getPublicField(Object instance, String fieldName, Class<T> fieldType) {
        try {
            Field field = AbstractMonster.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return fieldType.cast(field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlankPaper();
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
