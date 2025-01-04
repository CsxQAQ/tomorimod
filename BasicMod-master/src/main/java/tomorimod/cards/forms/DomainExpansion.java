package tomorimod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class DomainExpansion extends BaseFormCard {

    public static final String ID = makeID(DomainExpansion.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 0;

    public DomainExpansion() {
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
        return new DomainExpansion();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
