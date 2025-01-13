package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.AstronomyMinisterPower;
import tomorimod.powers.forms.AstronomyMinisterFormPower;
import tomorimod.util.CardStats;

public class AstronomyMinister extends BaseCard {
    public static final String ID = makeID(AstronomyMinister.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 2;

    public AstronomyMinister() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new ApplyPowerAction(p,p,new AstronomyMinisterPower(p,magicNumber),magicNumber));
    }


    @Override
    public AbstractCard makeCopy() {
        return new AstronomyMinister();
    }
}
