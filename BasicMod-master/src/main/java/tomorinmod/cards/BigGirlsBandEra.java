package tomorinmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.BigGirlsBandEraPower;
import tomorinmod.powers.Gravity;
import tomorinmod.util.CardStats;

public class BigGirlsBandEra extends BaseMonmentCard {

    public static final String ID = makeID(BigGirlsBandEra.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public BigGirlsBandEra() {
        super(ID, info);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BigGirlsBandEraPower(p,upgraded), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BigGirlsBandEra();
    }

    private void updateDescription() {

        this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.updateDescription();
        }
    }
}
