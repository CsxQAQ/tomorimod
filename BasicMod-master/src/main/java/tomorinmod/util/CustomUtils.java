package tomorinmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.BaseCard;

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
}
