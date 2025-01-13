package tomorimod.cards.notshow.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class GravityDomainForm extends BaseFormCard {

    public static final String ID = makeID(GravityDomainForm.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 0;

    public GravityDomainForm() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void setPowerName(){
        formName ="DomainExpansionPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new GravityDomainForm();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
