package tomorinmod.monitor;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.rare.MygoTogether;
import tomorinmod.cards.rare.TomorinApotheosis;

public class MygoTogetherMonitor extends BaseMonitor implements OnStartBattleSubscriber {

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        MygoTogether.isMygoTogetherUsed=false;
    }
}
