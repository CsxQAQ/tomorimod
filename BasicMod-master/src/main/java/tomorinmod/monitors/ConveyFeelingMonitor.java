package tomorinmod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.ConveyFeeling;

public class ConveyFeelingMonitor extends BaseMonitor implements OnStartBattleSubscriber, PostBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        ConveyFeeling.isConveyFeelingUsed=false;
        ConveyFeeling.maxHPOverflow=0;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        AbstractDungeon.player.decreaseMaxHealth(ConveyFeeling.maxHPOverflow);
    }
}
