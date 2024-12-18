package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.forms.*;
import tomorinmod.util.CardStats;

public class Pant extends BaseFormCard {

    public static final String ID = makeID(Pant.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Pant() {
        super(ID, info);
        setFormPower();
        baseMagicNumber=3;
    }

    @Override
    public void setFormPower(){
        if(!upgraded){
            formPower="PantPower";
        }else{
            formPower="PantPowerUpgraded";
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        if(!upgraded){
            addToBot(new ApplyPowerAction(p, p, new PantPower(p),1));
        }else{
            addToBot(new ApplyPowerAction(p, p, new PantPowerUpgraded(p),1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Pant();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            setFormPower();
            baseMagicNumber++;
        }
    }
}
