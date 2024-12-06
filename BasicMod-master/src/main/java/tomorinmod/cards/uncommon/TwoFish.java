package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.actions.CheckShineGravityAction;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;
import tomorinmod.util.CardStats;

public class TwoFish extends BaseCard {
    public static final String ID = makeID(TwoFish.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private int isFlipped = 0;

    private int POWERS=3;
    private int UPG_POWERS=2;


    public TwoFish() {
        super(ID, info);
        this.exhaust=true;
        setMagic(POWERS,UPG_POWERS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(isFlipped==0){
            addToBot(new ApplyPowerAction(p, p, new Gravity(p, magicNumber), magicNumber));
            addToBot(new CheckShineGravityAction(p));
        }else{
            addToBot(new ApplyPowerAction(p, p, new Shine(p, magicNumber), magicNumber));
            addToBot(new CheckShineGravityAction(p));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        AbstractPower shine = AbstractDungeon.player.getPower(Shine.POWER_ID);
        AbstractPower gravity = AbstractDungeon.player.getPower(Gravity.POWER_ID);

        if (shine != null) {
            isFlipped=1;
        }else if(gravity!=null){
            isFlipped=0;
        }
        this.updateDescription();
    }

    private void updateDescription() {
        if (this.isFlipped == 0) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        } else {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TwoFish();
    }
}
