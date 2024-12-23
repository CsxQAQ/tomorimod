package tomorinmod.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.WithoutMaterial;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;
import tomorinmod.util.CustomUtils;

import java.lang.reflect.WildcardType;

public class WholeLife extends BaseCard implements WithoutMaterial {
    public static final String ID = makeID(WholeLife.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    public WholeLife() {
        super(ID, info);
        CustomUtils.setRareBanner(this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new WholeLife();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
