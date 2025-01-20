package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.SmoothComboAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.savedata.customdata.CraftingRecipes;
import tomorimod.util.CardStats;

public class SmoothCombo extends BaseCard {
    public static final String ID = makeID(SmoothCombo.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    public SmoothCombo() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SmoothComboAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SmoothCombo();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
