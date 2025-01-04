package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class Revolution extends BaseCard {


    public static final String ID = makeID(Revolution.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );


    public static boolean isRevolutionUsed=false;

    public Revolution() {
        super(ID, info);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        isRevolutionUsed=true;
        BaseMonmentCard.removeFromMasterDeck(this);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Revolution();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}
