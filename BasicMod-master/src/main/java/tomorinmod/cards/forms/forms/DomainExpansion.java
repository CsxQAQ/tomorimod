package tomorinmod.cards.forms.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.forms.DomainExpansionPower;
import tomorinmod.util.CardStats;

public class DomainExpansion extends BaseFormCard {

    public static final String ID = makeID(DomainExpansion.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
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
