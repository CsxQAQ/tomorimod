package tomorinmod.monitors;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.music.Lunfuyu;

public class LunfuyuMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {

    @Override
    public void receiveOnPlayerTurnStart() {
        Lunfuyu.curHp=AbstractDungeon.player.currentHealth;
        Lunfuyu.hpChangeNum=0;
    }
}
