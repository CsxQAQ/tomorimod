package tomorinmod.monitor;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.BaseCard;

import java.lang.reflect.Field;

public class MomentTagMonitor implements OnStartBattleSubscriber {
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
