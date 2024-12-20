package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.SaiyakuAction;
import tomorinmod.cards.special.Band;
import tomorinmod.cards.special.HaAnon;
import tomorinmod.cards.special.HaTaki;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

public class Saiyaku extends BaseMonmentCard {

    public static final String ID = makeID(Saiyaku.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Saiyaku() {
        super(ID, info);
        this.exhaust=true;
        this.cardsToPreview=new HaTaki();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractCard> haGroups=new ArrayList<>();
        for(int i=0;i<10;i++){
            if (i % 2 == 0) {
                haGroups.add(new HaTaki());
            } else {
                haGroups.add(new HaAnon());
            }
        }

        for(int i=0;i<10;i++){
            if(upgraded){
                haGroups.get(i).upgrade();
            }
            addToBot(new SaiyakuAction(haGroups,i));
        }
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Saiyaku();
    }

    public void updateDescription(){
        if(upgraded){
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }else{
            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        }
    }


    @Override
    public void upgrade(){
        if(!upgraded){
            this.upgradeName();
            HaTaki upgradedHaTaki = new HaTaki();
            upgradedHaTaki.upgrade();
            this.updateDescription();
            this.cardsToPreview = upgradedHaTaki;
        }
    }
}
