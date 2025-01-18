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
import tomorimod.util.PlayerUtils;

public class LightAndShadow extends BaseCard {
    public static final String ID = makeID(LightAndShadow.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static int curAttribute=0;

    public static final int MAGIC=5;
    public static final int UPG_MAGIC=3;


    public LightAndShadow() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
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

        int shineAmount = PlayerUtils.getPowerNum(makeID("ShinePower"));
        int gravityAmount = PlayerUtils.getPowerNum(makeID("GravityPower"));

        if(gravityAmount>=shineAmount){
            curAttribute=0;
        }else{
            curAttribute=1;
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
