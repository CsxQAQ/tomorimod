package tomorinmod.monitor;

import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.basic.MusicComposition;
import tomorinmod.tags.CustomTags;

import java.util.ArrayList;
import java.util.Iterator;

public class CountUsedCardMonitor implements OnCardUseSubscriber,PostBattleSubscriber {


    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(abstractCard instanceof BaseCard){
            BaseCard baseCard=(BaseCard) abstractCard;
            if(baseCard.material!=""){
                MusicComposition.cardsUsed.add(abstractCard.cardID);
            }
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        MusicComposition.cardsUsed.clear();
    }
}
