package tomorimod.cards.monment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import tomorimod.character.Tomori;
import tomorimod.powers.TemporaryThornsPower;
import tomorimod.util.CardStats;

public class WishYouHappiness extends BaseMonmentCard {
    public static final String ID = makeID(WishYouHappiness.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public final static int BLOCK = 999;
    public final static int UPG_BLOCK = 0;
    public final static int MAGIC = 20;
    public final static int UPG_MAGIC = 0;

    public WishYouHappiness() {
        super(ID, info);
        setBlock(BLOCK,UPG_BLOCK);
        setMagic(MAGIC,UPG_MAGIC);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        if(!upgraded){
            addToBot(new ApplyPowerAction(p,p,new TemporaryThornsPower(p,magicNumber),magicNumber));
        }else{
            addToBot(new ApplyPowerAction(p,p,new ThornsPower(p,magicNumber),magicNumber));
        }
        super.use(p,m);
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
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.updateDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WishYouHappiness();
    }

}
