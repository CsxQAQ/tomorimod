package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.actions.ApplyShineAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class PoemInsteadOfSong extends BaseCard {
    public static final String ID = makeID(PoemInsteadOfSong.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public PoemInsteadOfSong() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shineNum = 0;

        for (AbstractPower power : p.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF) {
                shineNum += power.amount;
                addToBot(new RemoveSpecificPowerAction(p,p,power.ID));
            }
        }

        if (shineNum > 0) {
            addToBot(new ApplyShineAction(shineNum));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PoemInsteadOfSong();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}
