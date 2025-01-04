package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class EquelExchange extends BaseCard {
    public static final String ID = makeID(EquelExchange.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public EquelExchange() {
        super(ID, info);
        this.purgeOnUse=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractCard copy = this.makeStatEquivalentCopy();
        addToBot(new MakeTempCardInHandAction(copy));

        addToBot(new DrawCardAction(p, 1));

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new EquelExchange();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate=true;
        }
    }
}
