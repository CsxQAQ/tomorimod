package tomorinmod.cards.forms.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorinmod.character.MyCharacter;
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

    public final static int MAGIC = 2;
    public final static int UPG_MAGIC = 1;

    public AstronomyMinister() {
        super(ID, info);
        setFormPower();
        setMagic(MAGIC,UPG_MAGIC);
    }


    @Override
    public void setFormPower(){
//        if(!upgraded){
//            formPower="AstronomyMinisterPower";
//        }else{
//            formPower="AstronomyMinisterPowerUpgraded";
//        }
        formName ="AstronomyMinisterPower";
    }

//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        super.use(p,m);
////        if(!upgraded){
////            addToBot(new ApplyPowerAction(p, p, new AstronomyMinisterPower(p,1,magicNumber),1));
////        }else{
////            addToBot(new ApplyPowerAction(p, p, new AstronomyMinisterPowerUpgraded(p,1),1));
////        }
//        addToBot(new ApplyPowerAction(p, p, new AstronomyMinisterPower(p,1,magicNumber),1));
//    }

    @Override
    public AbstractCard makeCopy() {
        return new AstronomyMinister();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //setFormPower();
            upgradeMagicNumber(magicUpgrade);
        }
    }
}
