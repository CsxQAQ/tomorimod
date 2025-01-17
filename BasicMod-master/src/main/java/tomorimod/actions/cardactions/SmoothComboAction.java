package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tomorimod.cards.customcards.SmoothCombo;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.savedata.customdata.CraftingRecipes;

import java.util.Iterator;

import static tomorimod.TomoriMod.makeID;

public class SmoothComboAction extends AbstractGameAction {
    private AbstractCard card;
    public SmoothComboAction(AbstractCard card) {
        this.card=card;
    }


    @Override
    public void update() {
        CraftingRecipes.Material currentMaterial = AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(this.card);
        Iterator<AbstractCard> iterator = AbstractDungeon.player.drawPile.group.iterator();

        while (iterator.hasNext()) {
            AbstractCard card = iterator.next();
            if (AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card).equals(currentMaterial) ||
                    AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card).equals(CraftingRecipes.Material.AQUARIUMPASS)) {

                // 使用迭代器的 remove 方法移除当前元素
                iterator.remove();
                (AbstractDungeon.getCurrRoom()).souls.remove(card);

                // 将卡牌从手牌移到游戏中的其他区域，设置相关参数
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;

                card.applyPowers();
                addToTop(new NewQueueCardAction(card, true,false, true));
                addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
        }
        isDone = true;
    }
}
