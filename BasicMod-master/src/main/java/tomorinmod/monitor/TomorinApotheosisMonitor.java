package tomorinmod.monitor;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.rare.TomorinApotheosis;
import tomorinmod.cards.uncommon.GiftBox;

import static tomorinmod.BasicMod.makeID;

public class TomorinApotheosisMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber, PostBattleSubscriber {
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
    public void receivePostBattle(AbstractRoom abstractRoom) {
        TomorinApotheosis.isTomorinApotheosisUsed=false;
    }
}
