package tomorinmod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.custompowers.BigGirlsBandEraPower;
import tomorinmod.powers.custompowers.BigGirlsBandEraUpgradedPower;
import tomorinmod.util.CardStats;

public class BigGirlsBandEra extends BaseCard {

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
        if(upgraded){
            addToBot(new ApplyPowerAction(p, p, new BigGirlsBandEraUpgradedPower(p), 1));
        }else{
            addToBot(new ApplyPowerAction(p, p, new BigGirlsBandEraPower(p), 1));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new BigGirlsBandEra();
    }

    public void updateDescription() {

        if(upgraded){
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }else{
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        }
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
