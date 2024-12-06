package tomorinmod.monitor;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.uncommon.GiftBox;

import javax.smartcardio.Card;

import static tomorinmod.BasicMod.makeID;

public class GiftBoxFlipMonitor implements OnPlayerTurnStartSubscriber {
    @Override
    public void receiveOnPlayerTurnStart() {
        AbstractPlayer player = AbstractDungeon.player;

        for (AbstractCard card : player.hand.group) {
            if (card.cardID.equals(makeID("GiftBox"))) {
                if(card instanceof GiftBox){
                    GiftBox giftBox = (GiftBox) card;
                    if(!giftBox.isFlipped){
                        giftBox.flipCard();
                    }
                }
            }
        }
    }
}
