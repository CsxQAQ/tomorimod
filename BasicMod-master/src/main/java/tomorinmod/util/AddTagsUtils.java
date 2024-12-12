package tomorinmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.BaseCard;
import tomorinmod.tags.CustomTags;

import javax.smartcardio.Card;
import java.util.Iterator;

public class AddTagsUtils {
    public static void addTags(BaseCard aCard, AbstractCard.CardTags tag){
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (aCard.uuid.equals(card.uuid)) {
                card.tags.add(tag);
                break;
            }
        }
    }
}
