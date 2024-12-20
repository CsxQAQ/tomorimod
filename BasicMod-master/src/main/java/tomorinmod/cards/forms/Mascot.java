package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Mascot extends BaseFormCard {

    public static final String ID = makeID(Mascot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 2;
    public final static int UPG_MAGIC = 0;

    public Mascot() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void setPowerName(){
        formName ="MascotPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mascot();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
