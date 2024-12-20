package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Pant extends BaseFormCard {

    public static final String ID = makeID(Pant.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 1;

    public Pant() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void setPowerName(){
        formName ="PantPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new Pant();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(magicUpgrade);
        }
    }
}
