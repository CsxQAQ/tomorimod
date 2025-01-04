package tomorimod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class StrengthTomori extends BaseFormCard {

    public static final String ID = makeID(StrengthTomori.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 1;
    public final static int UPG_MAGIC = 0;


    public StrengthTomori() {
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
        return new StrengthTomori();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
