package tomorimod.cards.special;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class FearlessFear extends BaseCard implements SpecialCard, WithoutMaterial {
    public static final String ID = makeID(FearlessFear.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.SELF,
            1
    );

    public FearlessFear() {
        super(ID, info);
        selfRetain=true;

    }

    @Override
    public boolean canUse(AbstractPlayer p,AbstractMonster m){
        return false;
    }

    @SpireOverride
    public void renderEnergy(SpriteBatch sb){

    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new FearlessFear();
    }


}
