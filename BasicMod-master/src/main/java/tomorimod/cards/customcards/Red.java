package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class Red extends BaseCard {

    public static final String ID = makeID(Red.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC = 1;
    public static final int UPG_MAGIC = 1;

    public Red() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        //CustomUtils.setRareBanner(this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,magicNumber),magicNumber));
        //addToBot(new DrawCardAction(magicNumber));
        //addToBot(new GainEnergyAction(1));

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Red();
    }

}
