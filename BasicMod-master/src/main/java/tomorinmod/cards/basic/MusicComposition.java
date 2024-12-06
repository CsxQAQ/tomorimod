package tomorinmod.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.monitor.CountUsedCardMonitor;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

public class MusicComposition extends BaseCard {
    public static final String ID = makeID(MusicComposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    private boolean isFliped=false;
    private ArrayList<String> cardsUsed=new ArrayList<>();
    private int currentLength;
    private int startLength;

    public MusicComposition() {
        super(ID, info);
        this.exhaust=true;
        this.selfRetain = true;
        this.isInnate = true;

        this.currentLength =CountUsedCardMonitor.cardsUsed.size();
        this.startLength=CountUsedCardMonitor.cardsUsed.size();
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return this.isFliped;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(!this.isFliped&&this.currentLength -this.startLength <3){
            if(this.currentLength !=CountUsedCardMonitor.cardsUsed.size()){
                this.cardsUsed.add(CountUsedCardMonitor.cardsUsed.get(CountUsedCardMonitor.cardsUsed.size()-1));
                this.currentLength++;
            }
        }
        if(this.currentLength -this.startLength==3){
            this.isFliped=true;
        }
        updateDescription();
    }

    private void updateDescription() {
        if (!this.isFliped) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        } else {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0]+this.cardsUsed.toString();
        }
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractCard copyCard = this.makeCopy();

        p.hand.addToHand(copyCard);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MusicComposition();
    }
}
