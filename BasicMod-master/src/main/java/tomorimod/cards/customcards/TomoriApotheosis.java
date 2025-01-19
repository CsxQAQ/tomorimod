package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.TomoriApotheosisPower;
import tomorimod.util.CardStats;

public class TomoriApotheosis extends BaseCard {


    public static final String ID = makeID(TomoriApotheosis.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public TomoriApotheosis() {
        super(ID, info);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new TomoriApotheosisPower(p,1),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TomoriApotheosis();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            updateDescription();
            isInnate=true;
        }
    }

    public void updateDescription(){
        if(upgraded){
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }else{
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        }
        initializeDescription();
    }

}
