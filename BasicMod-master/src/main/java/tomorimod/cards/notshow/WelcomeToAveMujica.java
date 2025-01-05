package tomorimod.cards.notshow;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.util.CardStats;

public class WelcomeToAveMujica extends BaseCard implements SpecialCard, WithoutMaterial {
    public static final String ID = makeID(WelcomeToAveMujica.class.getSimpleName());
    public static final CardStats info = new CardStats(
            //Tomori.Meta.CARD_COLOR,
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.SELF,
            -2
    );

    public WelcomeToAveMujica() {
        super(ID, info);
        this.isEthereal = true;

    }

    @Override
    public boolean canUse(AbstractPlayer p,AbstractMonster m){
        return false;
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new WelcomeToAveMujica();
    }


}
