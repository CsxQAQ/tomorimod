package tomorinmod.monitors;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.music.Lunfuyu;
import tomorinmod.cards.rare.ConveyFeeling;

public class LunfuyuMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {

    @Override
    public void receiveOnPlayerTurnStart() {
        Lunfuyu.curHp=AbstractDungeon.player.currentHealth;
        Lunfuyu.hpChangeNum=0;
    }
}
