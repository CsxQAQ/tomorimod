package tomorimod.monitors;

import basemod.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.permanentforms.PermanentFrom;
import tomorimod.savedata.customdata.CardsRemovedFromPoolSaveData;

import java.util.Iterator;

public class RemoveCardsFromPoolMonitor extends BaseMonitor implements PostUpdateSubscriber, PostDungeonInitializeSubscriber,StartGameSubscriber {

    private int previousDeckSize = 0;
    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player != null) {
            int currentDeckSize = AbstractDungeon.player.masterDeck.size();
            if (currentDeckSize > previousDeckSize) {
                AbstractCard newCard = AbstractDungeon.player.masterDeck.group.get(currentDeckSize - 1);
                if(newCard instanceof PermanentFrom){
                    onCardObtained(newCard);
                }
                previousDeckSize = currentDeckSize;
            }
        }
    }

    public void onCardObtained(AbstractCard card) {
        Iterator<AbstractCard> iterator = AbstractDungeon.rareCardPool.group.iterator();
        while (iterator.hasNext()) {
            AbstractCard c = iterator.next();
            if (c.cardID.equals(card.cardID)) {
                iterator.remove();
                CardsRemovedFromPoolSaveData.getInstance().cardsRemoved.add(card.cardID);
            }
        }
    }

    @Override
    public void receivePostDungeonInitialize() { //重进游戏好像不会执行到这个
        if(AbstractDungeon.floorNum==0) {
            CardsRemovedFromPoolSaveData.getInstance().clear();
        }else{
            Iterator<AbstractCard> iterator = AbstractDungeon.rareCardPool.group.iterator();
            while (iterator.hasNext()) {
                AbstractCard c = iterator.next();
                for(String cardID:CardsRemovedFromPoolSaveData.getInstance().cardsRemoved){
                    if(c.cardID.equals(cardID)){
                        iterator.remove();
                    }
                }
            }
        }

    }

    @Override
    public void receiveStartGame() {
        for(String ID:CardsRemovedFromPoolSaveData.getInstance().cardsRemoved){
            Iterator<AbstractCard> iterator = AbstractDungeon.rareCardPool.group.iterator();
            while (iterator.hasNext()) {
                AbstractCard c = iterator.next();
                if (c.cardID.equals(ID)) {
                    iterator.remove();
                }
            }
        }
    }
}
