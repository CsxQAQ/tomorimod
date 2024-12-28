package tomorimod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class DarkTomori extends BaseFormCard {

    public static final String ID = makeID(DarkTomori.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 1;
    public final static int UPG_MAGIC = 0;

    public DarkTomori() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
        exhaust=true;
    }

    @Override
    public void setPowerName(){
        formName ="DarkTomoriPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkTomori();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
