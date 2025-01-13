package tomorimod.cards.notshow.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class StrengthTomoriForm extends BaseFormCard {

    public static final String ID = makeID(StrengthTomoriForm.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 1;
    public final static int UPG_MAGIC = 0;


    public StrengthTomoriForm() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
    }


    @Override
    public void setPowerName(){
        formName ="StrengthTomoriPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrengthTomoriForm();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
