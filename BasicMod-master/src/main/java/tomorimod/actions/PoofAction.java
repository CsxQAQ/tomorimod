package tomorimod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import tomorimod.cards.notshow.utilcards.FailComposition;
import tomorimod.powers.GravityPower;

public class PoofAction extends AbstractGameAction {

    private AbstractCard card;
    public PoofAction(AbstractCard card) {
        this.card=card;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.contains(card)) {
            AbstractDungeon.player.hand.removeCard(card);
        }
        if (AbstractDungeon.player.limbo.contains(card)) {
            AbstractDungeon.player.limbo.removeCard(card);
        }

        AbstractDungeon.effectList.add(new ExhaustCardEffect(card));

        isDone = true;
    }

}
