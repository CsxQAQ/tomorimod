package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class InterestingGirl extends BaseMonmentCard {

    public static final String ID = makeID(InterestingGirl.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            -1
    );

    public InterestingGirl() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int energyNum=this.energyOnUse;
        if(!upgraded){
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, energyNum), energyNum));
        }else{
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 2*energyNum), 2*energyNum));
        }
        p.energy.use(energyNum);
        super.use(p,m);
    }

    public void updateDescription(){
        if(upgraded){
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }else{
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            updateDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new InterestingGirl();
    }
}
