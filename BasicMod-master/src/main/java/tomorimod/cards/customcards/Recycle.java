package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.MyCharacter;
import tomorimod.util.CardStats;

import java.util.ArrayList;

public class Recycle extends BaseCard {
    public static final String ID = makeID(Recycle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Recycle() {
        super(ID, info);
        purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> tempList = new ArrayList<>(p.exhaustPile.group);
                for (AbstractCard c : tempList) {
                    if(!c.type.equals(CardRarity.CURSE)&&!c.type.equals(CardType.STATUS)){
                        p.drawPile.addToTop(c.makeStatEquivalentCopy());
                    }
                }
                isDone=true;
            }
        });
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                p.exhaustPile.clear();
                isDone=true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new Recycle();
    }

}
