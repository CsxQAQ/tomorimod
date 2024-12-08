package tomorinmod.monitor;

import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.basic.MusicComposition;
import tomorinmod.tags.CustomTags;

import java.util.ArrayList;
import java.util.Iterator;

public class CountUsedCardMonitor implements OnCardUseSubscriber,PostBattleSubscriber {


    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        String cardName = abstractCard.cardID;
        MusicComposition.cardsUsed.add(cardName);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        MusicComposition.cardsUsed.clear();
    }
}
