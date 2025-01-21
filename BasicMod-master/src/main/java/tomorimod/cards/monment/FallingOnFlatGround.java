package tomorimod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.FallingOnFlatGroundAction;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class FallingOnFlatGround extends BaseMonmentCard {

    public static final String ID = makeID(FallingOnFlatGround.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC = 1;
    public static final int UPG_MAGIC = 1;

    public FallingOnFlatGround() {
        super(ID, info);
        this.exhaust=true;
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < magicNumber; i++) {
            addToBot(new FallingOnFlatGroundAction(upgraded));
        }
        super.use(p,m);
    }

//    public void updateDescription(){
//        if(upgraded){
//            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
//        }else{
//            rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
//        }
//        initializeDescription();
//    }

//    @Override
//    public void upgrade() {
//        if (!upgraded) {
//            upgradeName();
//            updateDescription();
//        }
//    }

    @Override
    public AbstractCard makeCopy() {
        return new FallingOnFlatGround();
    }
}
