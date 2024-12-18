package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.forms.SingerPower;
import tomorinmod.powers.forms.SingerPowerUpgraded;
import tomorinmod.util.CardStats;

public class Singer extends BaseFormCard {

    public static final String ID = makeID(Singer.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Singer() {
        super(ID, info);
        setFormPower();
        baseMagicNumber=1;
    }

    @Override
    public void setFormPower(){
        if(!upgraded){
            formPower="SingerPower";
        }else{
            formPower="SingerPowerUpgraded";
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);  //super得放上面
        if(!upgraded){
            addToBot(new ApplyPowerAction(p, p, new SingerPower(p),1));
        }else{
            addToBot(new ApplyPowerAction(p, p, new SingerPowerUpgraded(p),1));
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Singer();
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
