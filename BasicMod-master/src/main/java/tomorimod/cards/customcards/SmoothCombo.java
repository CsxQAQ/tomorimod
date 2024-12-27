package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.MyCharacter;
import tomorimod.util.CardStats;

public class SmoothCombo extends BaseCard {
    public static final String ID = makeID(SmoothCombo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public static int smoothComboUseTime=0;
    public static CardType recordedType = null;
    public SmoothCombo() {
        super(ID, info);
    }


    public static void reset(){
        smoothComboUseTime = 0;
        recordedType=null;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        smoothComboUseTime=1;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SmoothCombo();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
