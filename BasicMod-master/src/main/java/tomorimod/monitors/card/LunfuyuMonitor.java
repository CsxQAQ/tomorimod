package tomorimod.monitors.card;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.monitors.BaseMonitor;

public class LunfuyuMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber, PostUpdateSubscriber {

    public static int curHp=0;
    public static int hpIncreaseNum=0;
    public static int hpChangeNum=0;

    @Override
    public void receiveOnPlayerTurnStart() {
        if(AbstractDungeon.player!=null){
            curHp=AbstractDungeon.player.currentHealth;
            hpIncreaseNum=0;
            hpChangeNum=0;
        }

    }

    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player != null) {
            int newHp = AbstractDungeon.player.currentHealth;

            if (newHp != curHp) {
                int delta = newHp - curHp;
                hpChangeNum += Math.abs(delta);

                if (delta > 0) {
                    hpIncreaseNum += delta;
                }
                curHp = newHp;
            }
        }
    }
}
