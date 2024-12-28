package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.actions.ApplyShineAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.util.CardStats;

public class LightAndShadow extends BaseCard {
    public static final String ID = makeID(LightAndShadow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static int curAttribute=0;

    private int POWERS=3;
    private int UPG_POWERS=2;


    public LightAndShadow() {
        super(ID, info);
        this.exhaust=true;
        setMagic(POWERS,UPG_POWERS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(curAttribute==0){
            addToBot(new ApplyGravityAction(magicNumber));
        }else{
            addToBot(new ApplyShineAction(magicNumber));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        AbstractPower shine = AbstractDungeon.player.getPower(ShinePower.POWER_ID);
        AbstractPower gravity = AbstractDungeon.player.getPower(GravityPower.POWER_ID);

        int shineAmount = (shine != null) ? shine.amount : 0;
        int gravityAmount = (gravity != null) ? gravity.amount : 0;

        if(curAttribute==0){
            if(shineAmount>gravityAmount){
                curAttribute=1;
            }
        }else{
            if(gravityAmount>shineAmount){
                curAttribute=0;
            }
        }

        this.updateDescription();
    }

    public void updateDescription() {
        if (curAttribute==0) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        } else {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LightAndShadow();
    }
}
