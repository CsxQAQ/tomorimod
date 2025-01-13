package tomorimod.cards.notshow.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class PantForm extends BaseFormCard {

    public static final String ID = makeID(PantForm.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 5;
    public final static int UPG_MAGIC = 3;

    public PantForm() {
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
        return new PantForm();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(magicUpgrade);
        }
    }
}
