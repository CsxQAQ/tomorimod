package tomorinmod.monitors;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.TomorinApotheosis;

public class TomorinApotheosisMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber, OnStartBattleSubscriber {
    @Override
    public void receiveOnPlayerTurnStart() {
        if(TomorinApotheosis.isTomorinApotheosisUsed){
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                card.upgrade();
            }

            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                card.upgrade();
            }

            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                card.upgrade();
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        TomorinApotheosis.isTomorinApotheosisUsed=false;
    }
}
