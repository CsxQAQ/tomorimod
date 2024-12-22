
package tomorinmod.cards.special;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.WithoutMaterial;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

//这个设置为BaseMusicCard可能会出问题
public class FailComposition extends BaseCard implements WithoutMaterial {
    public static final String ID = makeID(FailComposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            0
    );

    public FailComposition() {
        super(ID, info);
        isEthereal = true;
    }

    @Override
    public boolean canUpgrade(){
        return false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new FailComposition();
    }

}
