package tomorimod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.SaiyakuAction;
import tomorimod.cards.notshow.utilcards.HaAnon;
import tomorimod.cards.notshow.utilcards.HaTaki;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

import java.util.ArrayList;

public class Saiyaku extends BaseMonmentCard {

    public static final String ID = makeID(Saiyaku.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
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
        initializeDescription();
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
