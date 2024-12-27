//package tomorimod.monitors;
//
//import basemod.interfaces.OnPlayerTurnStartSubscriber;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import tomorimod.cards.monment.GiftBox;
//
//import static tomorimod.BasicMod.makeID;
//
//public class GiftBoxFlipMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {
//    @Override
//    public void receiveOnPlayerTurnStart() {
//        AbstractPlayer player = AbstractDungeon.player;
//
//        for (AbstractCard card : player.hand.group) {
//            if (card.cardID.equals(makeID("GiftBox"))) {
//                if(card instanceof GiftBox){
//                    GiftBox giftBox = (GiftBox) card;
//                    if(!giftBox.isFlipped){
//                        giftBox.flipCard();
//                    }
//                }
//            }
//        }
//    }
//}
