package tomorimod.cards.notshow.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class AstronomyMinisterForm extends BaseFormCard {
    public static final String ID = makeID(AstronomyMinisterForm.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 1;

    public AstronomyMinisterForm() {
        super(ID, info);
        setPowerName();
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        super.use(p,m);
    }

    @Override
    public void setPowerName(){
        formName ="AstronomyMinisterPower";
    }

    @Override
    public AbstractCard makeCopy() {
        return new AstronomyMinisterForm();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(magicUpgrade);
        }
    }
}
