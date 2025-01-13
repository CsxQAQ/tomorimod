package tomorimod.cards.notshow.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.cards.notshow.SpecialCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class MascotForm extends BaseFormCard implements SpecialCard {

    public static final String ID = makeID(MascotForm.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    public final static int MAGIC = 2;
    public final static int UPG_MAGIC = 1;

    public MascotForm() {
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
        return new MascotForm();
    }

}
