package tomorinmod.monitor;

import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.tags.CustomTags;

import java.util.ArrayList;
import java.util.Iterator;

public class CountUsedCardMonitor implements OnCardUseSubscriber,PostBattleSubscriber {

    public static ArrayList<String> cardsUsed= new ArrayList<>();

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        String cardName = abstractCard.cardID; // 使用 cardID 来唯一标识卡片，或者可以使用 card.name
        cardsUsed.add(cardName);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        cardsUsed.clear();
    }
}
