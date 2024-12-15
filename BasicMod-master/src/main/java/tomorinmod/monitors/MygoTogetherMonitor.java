package tomorinmod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.customcards.MygoTogether;

public class MygoTogetherMonitor extends BaseMonitor implements OnStartBattleSubscriber {

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        MygoTogether.isMygoTogetherUsed=false;
    }
}
