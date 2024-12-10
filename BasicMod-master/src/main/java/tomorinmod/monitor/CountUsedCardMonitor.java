package tomorinmod.monitor;

import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.special.Lyric;

public class CountUsedCardMonitor extends BaseMonitor implements OnCardUseSubscriber, OnStartBattleSubscriber {


    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(abstractCard instanceof BaseCard){
            BaseCard baseCard=(BaseCard) abstractCard;
            if(baseCard.material!=""){
                Lyric.cardsUsed.add(abstractCard.cardID);
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        Lyric.cardsUsed.clear();
    }
}
