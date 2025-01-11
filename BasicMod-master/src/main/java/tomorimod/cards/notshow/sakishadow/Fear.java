package tomorimod.cards.notshow.sakishadow;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.notshow.SpecialCard;
import tomorimod.util.CardStats;

public class Fear extends BaseCard implements SpecialCard, WithoutMaterial, SakiShadow {
    public static final String ID = makeID(Fear.class.getSimpleName());
    public static final CardStats info = new CardStats(
            //Tomori.Meta.CARD_COLOR,
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.NONE,
            -2
    );

    public Fear() {
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
    public boolean canUpgrade(){
        return false;
    }

    @Override
    public void upgrade(){

    }
    @Override
    public AbstractCard makeCopy() {
        return new Fear();
    }


}
