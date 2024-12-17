package tomorinmod.util;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import tomorinmod.cards.BaseCard;

import java.util.ArrayList;

public class CustomUtils {
    public static void addTags(BaseCard aCard, AbstractCard.CardTags tag){
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (aCard.uuid.equals(card.uuid)) {
                card.tags.add(tag);
                break;
            }
        }
    }

    public static String idToName(String id) {
        if (id.contains(":")) {
            return id.split(":")[1]; // 获取 ":" 后的部分
        }
        return id; // 如果没有 ":"，直接返回
    }

    public static ArrayList<AbstractCard> getAllModCards() {
        ArrayList<AbstractCard> modCards = BaseMod.getCustomCardsToAdd();
        return modCards;
    }

//    public static ArrayList<AbstractCard> getAllModCards() {
//        ArrayList<AbstractCard> modCards = CardLibrary.getAllCards();
//        return modCards;
//    }


}
