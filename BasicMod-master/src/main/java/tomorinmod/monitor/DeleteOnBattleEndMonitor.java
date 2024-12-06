package tomorinmod.monitor;

import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.tags.CustomTags;

import java.util.Iterator;

public class DeleteOnBattleEndMonitor implements PostBattleSubscriber {
    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        Iterator<AbstractCard> iterator = AbstractDungeon.player.masterDeck.group.iterator();

        while (iterator.hasNext()) {
            AbstractCard card = iterator.next();
            if (card.hasTag(CustomTags.MOMENT)) {
                iterator.remove();
            }
        }
    }
}
