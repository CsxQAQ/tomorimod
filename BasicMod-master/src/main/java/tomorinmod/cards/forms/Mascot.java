package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Mascot extends BaseFormCard {

    public static final String ID = makeID(Mascot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    public final static int MAGIC = 2;
    public final static int UPG_MAGIC = 1;

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

}
