package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Singer extends BaseFormCard {
    public static final String ID = makeID(Singer.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 2;
    public final static int UPG_MAGIC = 0;


    public Singer() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void setPowerName() {
        formName = "SingerPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new Singer();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
