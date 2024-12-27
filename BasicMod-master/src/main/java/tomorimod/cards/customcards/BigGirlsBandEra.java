package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.MyCharacter;
import tomorimod.powers.custompowers.BigGirlsBandEraPower;
import tomorimod.powers.custompowers.BigGirlsBandEraUpgradedPower;
import tomorimod.util.CardStats;

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
            addToBot(new ApplyPowerAction(p, p, new BigGirlsBandEraUpgradedPower(p,1), 1));
        }else{
            addToBot(new ApplyPowerAction(p, p, new BigGirlsBandEraPower(p,1), 1));
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
