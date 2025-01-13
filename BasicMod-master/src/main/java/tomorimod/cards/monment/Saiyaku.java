package tomorimod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.SaiyakuAction;
import tomorimod.cards.notshow.utilcards.Ha;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

import java.util.ArrayList;

import static tomorimod.TomoriMod.imagePath;

public class Saiyaku extends BaseMonmentCard {

    public static final String ID = makeID(Saiyaku.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC=10;
    public static final int UPG_MAGIC=10;

    public static final int INCREASE=3;

    public Saiyaku() {
        super(ID, info);
        this.exhaust=true;
        this.cardsToPreview=new Ha();
        setCustomVar("M2",INCREASE);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractCard> haGroups=new ArrayList<>();
        for(int i=0;i<magicNumber;i++){
            Ha ha=new Ha();
            if (i % 2 == 1) {
                ha.loadCardImage(imagePath("cards/attack/HaAnon.png"));
            }
            haGroups.add(ha);
        }

        for(int i=0;i<magicNumber;i++){
            addToBot(new SaiyakuAction(haGroups,i));
        }
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Saiyaku();
    }

}
