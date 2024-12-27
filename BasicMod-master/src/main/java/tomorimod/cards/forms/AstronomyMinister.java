package tomorimod.cards.forms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.character.MyCharacter;
import tomorimod.util.CardStats;

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
        return new AstronomyMinister();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(magicUpgrade);
        }
    }
}
