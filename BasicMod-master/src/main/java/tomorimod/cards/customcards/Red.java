package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class Red extends BaseCard {

    public static final String ID = makeID(Red.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Red() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        //CustomUtils.setRareBanner(this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new GainEnergyAction(1));

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Red();
    }

}
