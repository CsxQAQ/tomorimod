package tomorinmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.BaseCard;
import tomorinmod.tags.CustomTags;

import javax.smartcardio.Card;
import java.util.Iterator;

public class AddTagsUtils {
    public static void addTags(BaseCard aCard, AbstractCard.CardTags tag){
        if(!aCard.isFromMasterDeck){
            return;
        }
        Iterator<AbstractCard> iterator = AbstractDungeon.player.masterDeck.group.iterator();
        while (iterator.hasNext()) {
            AbstractCard card = iterator.next();
            if (aCard.cardID.equals(card.cardID)) {
                if (!card.hasTag(tag)) {
                    card.tags.add(tag);
                    break;
                }
            }
        }
    }
}
