package tomorinmod.cards.forms.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.forms.MascotPower;
import tomorinmod.util.CardStats;

public class Mascot extends BaseFormCard {

    public static final String ID = makeID(Mascot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Mascot() {
        super(ID, info);
        setFormPower();
    }

    @Override
    public void setFormPower(){
        formName ="MascotPower";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        addToBot(new ApplyPowerAction(p, p, new MascotPower(p,1),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mascot();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
