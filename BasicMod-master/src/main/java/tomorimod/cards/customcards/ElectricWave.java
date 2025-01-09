package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.SoulViewAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class ElectricWave extends BaseCard {
    public static final String ID = makeID(ElectricWave.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public ElectricWave() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SoulViewAction(1));
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
            exhaust=false;
            this.updateDescription();
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new ElectricWave();
    }

}
