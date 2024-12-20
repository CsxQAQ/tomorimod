package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class DarkTomorin extends BaseFormCard {

    public static final String ID = makeID(DarkTomorin.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 1;
    public final static int UPG_MAGIC = 0;

    public DarkTomorin() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
        exhaust=true;
    }

    @Override
    public void setPowerName(){
        formName ="DarkTomorinPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkTomorin();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
