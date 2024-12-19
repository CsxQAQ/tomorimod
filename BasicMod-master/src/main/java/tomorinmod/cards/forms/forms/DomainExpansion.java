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

    public DomainExpansion() {
        super(ID, info);
        setPowerName();
    }

    @Override
    public void setPowerName(){
        formName ="DomainExpansionPower";
    }

//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        super.use(p,m);
//        addToBot(new ApplyPowerAction(p, p, new DomainExpansionPower(p,1),1));
//    }

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
