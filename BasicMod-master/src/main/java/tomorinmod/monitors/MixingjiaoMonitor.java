package tomorinmod.monitors;

import basemod.interfaces.OnCardUseSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.music.Mixingjiao;
import tomorinmod.tags.CustomTags;

import static tomorinmod.BasicMod.makeID;

public class MixingjiaoMonitor extends BaseMonitor implements OnCardUseSubscriber {

    private final String s = makeID("Mixingjiao");

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (abstractCard.hasTag(CustomTags.MUSIC)&&!(abstractCard.cardID.equals(s))) {

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

        }
    }

}
