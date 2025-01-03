package tomorimod.monitors.card;

import basemod.interfaces.OnCardUseSubscriber;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.cards.music.Mixingjiao;
import tomorimod.monitors.BaseMonitor;
import tomorimod.tags.CustomTags;

import static tomorimod.TomoriMod.makeID;

public class MixingjiaoMonitor extends BaseMonitor implements OnCardUseSubscriber {

    private final String s = makeID("Mixingjiao");

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (abstractCard instanceof BaseMusicCard &&!(abstractCard.cardID.equals(s))) {

            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (s.equals(card.cardID)&&card instanceof Mixingjiao) {
                    Mixingjiao mixingjiao=(Mixingjiao)card;
                    mixingjiao.autoUse();
                }
            }

            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (s.equals(card.cardID)&&card instanceof Mixingjiao) {
                    Mixingjiao mixingjiao=(Mixingjiao)card;
                    mixingjiao.autoUse();
                }
            }

            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (s.equals(card.cardID)&&card instanceof Mixingjiao) {
                    Mixingjiao mixingjiao=(Mixingjiao)card;
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(mixingjiao, true,
                            0, true, true));
                }
            }

        }
    }

}
