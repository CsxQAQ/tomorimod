package tomorimod.monitors.card;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.monitors.BaseMonitor;

public class MiluriMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber, PostUpdateSubscriber, OnStartBattleSubscriber {

    public static int curHp=0;
    public static int hpIncreaseNum=0;
    public static int hpIncreaseWholeBattle=0;
    public static int hpChangeNum=0;
    public static int hpChangeNumWholeBattle=0;

    @Override
    public void receiveOnPlayerTurnStart() {
        hpIncreaseNum=0;
        hpChangeNum=0;
    }

    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player != null) {
            int newHp = AbstractDungeon.player.currentHealth;

            if (newHp != curHp) {
                int delta = newHp - curHp;
                hpChangeNum += Math.abs(delta);
                hpChangeNumWholeBattle+=Math.abs(delta);

                if (delta > 0) {
                    hpIncreaseNum += delta;
                    hpIncreaseWholeBattle+=delta;
                }
                curHp = newHp;
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        curHp=AbstractDungeon.player.currentHealth;
        hpIncreaseWholeBattle=0;
        hpChangeNumWholeBattle=0;
        hpIncreaseNum=0;
        hpChangeNum=0;
    }
}
