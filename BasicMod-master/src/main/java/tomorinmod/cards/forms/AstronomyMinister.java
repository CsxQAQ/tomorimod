package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.forms.AstronomyMinisterPower;
import tomorinmod.powers.forms.AstronomyMinisterPowerUpgraded;
import tomorinmod.powers.forms.SingerPower;
import tomorinmod.powers.forms.SingerPowerUpgraded;
import tomorinmod.util.CardStats;

public class AstronomyMinister extends BaseFormCard {
    public static final String ID = makeID(AstronomyMinister.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public AstronomyMinister() {
        super(ID, info);
        setFormPower();
        baseMagicNumber=2;
    }

    @Override
    public void setFormPower(){
        if(!upgraded){
            formPower="AstronomyMinisterPower";
        }else{
            formPower="AstronomyMinisterPowerUpgraded";
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        if(!upgraded){
            addToBot(new ApplyPowerAction(p, p, new AstronomyMinisterPower(p),1));
        }else{
            addToBot(new ApplyPowerAction(p, p, new AstronomyMinisterPowerUpgraded(p),1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AstronomyMinister();
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
