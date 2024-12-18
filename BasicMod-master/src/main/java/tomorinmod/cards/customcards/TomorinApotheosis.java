package tomorinmod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.TomorinApotheosisPower;
import tomorinmod.util.CardStats;

public class TomorinApotheosis extends BaseCard {

    public static boolean isTomorinApotheosisUsed=false;

    public static final String ID = makeID(TomorinApotheosis.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public TomorinApotheosis() {
        super(ID, info);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new TomorinApotheosisPower(p),0));
        isTomorinApotheosisUsed=true;

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TomorinApotheosis();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradesDescription();
            this.selfRetain=true;
        }
    }

    private void upgradesDescription() {
        if (upgraded) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+"， 固有 。";
        }
    }



}
