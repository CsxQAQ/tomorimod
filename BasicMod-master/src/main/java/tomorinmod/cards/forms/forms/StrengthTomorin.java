package tomorinmod.cards.forms.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.forms.StrengthTomorinPower;
import tomorinmod.util.CardStats;

public class StrengthTomorin extends BaseFormCard {

    public static final String ID = makeID(StrengthTomorin.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public final static int MAGIC = 1;
    public final static int UPG_MAGIC = 0;


    public StrengthTomorin() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
    }


    @Override
    public void setPowerName(){
        formName ="StrengthTomorinPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrengthTomorin();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
