package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.powers.custompowers.SmoothComboPower;
import tomorimod.savedata.customdata.CraftingRecipes;
import tomorimod.util.CardStats;

public class SmoothCombo extends BaseCard {
    public static final String ID = makeID(SmoothCombo.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );


    public SmoothCombo() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CraftingRecipes.Material currentMaterial = AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(this);
        for(AbstractCard card:p.drawPile.group){
            if(AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card).equals(currentMaterial)||
                    AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card).equals(CraftingRecipes.Material.AQUARIUMPASS)){
                addToBot(new NewQueueCardAction(card,true,true,true));
            }
        }
//        addToBot(new ApplyPowerAction(p,p,new SmoothComboPower(p,1),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SmoothCombo();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
