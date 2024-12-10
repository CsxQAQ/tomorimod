package tomorinmod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.actions.CheckShineGravityAction;
import tomorinmod.powers.Gravity;

public class GetPowerAtFirstTurnMonitor extends BaseMonitor implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(player, player, new Gravity(player, 1), 1)
            );
            AbstractDungeon.actionManager.addToBottom(new CheckShineGravityAction(player));
        }

    }
}
