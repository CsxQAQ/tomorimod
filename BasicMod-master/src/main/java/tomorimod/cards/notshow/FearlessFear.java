package tomorimod.cards.notshow;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.util.CardStats;

public class FearlessFear extends BaseCard implements SpecialCard, WithoutMaterial,SakiShadow {
    public static final String ID = makeID(FearlessFear.class.getSimpleName());
    public static final CardStats info = new CardStats(
            //Tomori.Meta.CARD_COLOR,
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.NONE,
            -2
    );

    public FearlessFear() {
        super(ID, info);
        selfRetain=true;

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
        return new FearlessFear();
    }


}
