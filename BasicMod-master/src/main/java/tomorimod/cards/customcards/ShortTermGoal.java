package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.FailAndRestartAction;
import tomorimod.actions.cardactions.ShortTermGoalAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class ShortTermGoal extends BaseCard {
    public static final String ID = makeID(ShortTermGoal.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public ShortTermGoal() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ShortTermGoalAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new ShortTermGoal();
    }

}
