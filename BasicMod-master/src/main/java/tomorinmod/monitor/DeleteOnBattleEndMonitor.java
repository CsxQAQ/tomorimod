package tomorinmod.monitor;

import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.rare.TomorinApotheosis;
import tomorinmod.tags.CustomTags;

import java.util.Iterator;

public class DeleteOnBattleEndMonitor implements PostBattleSubscriber, OnStartBattleSubscriber {

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        //删除瞬间牌
        Iterator<AbstractCard> iterator = AbstractDungeon.player.masterDeck.group.iterator();

        while (iterator.hasNext()) {
            AbstractCard card = iterator.next();
            if (card.hasTag(CustomTags.MOMENT)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if(card instanceof BaseCard){
                BaseCard baseCard =(BaseCard)card;
                baseCard.isFromMasterDeck=true;
            }
        }
    }
}
