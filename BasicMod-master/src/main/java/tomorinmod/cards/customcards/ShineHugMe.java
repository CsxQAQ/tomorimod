package tomorinmod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.ShinePower;
import tomorinmod.util.CardStats;

public class ShineHugMe extends BaseCard {
    public static final String ID = makeID(ShineHugMe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public ShineHugMe() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower shine = p.getPower(ShinePower.POWER_ID);
        if (shine != null) {
            for (int i = 0; i < shine.amount; i++) {
                addToBot(new GainEnergyAction(1));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShineHugMe();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
