package tomorinmod.monitor;

import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.actions.SmoothComboAction;
import tomorinmod.cards.rare.TomorinApotheosis;
import tomorinmod.cards.uncommon.SmoothCombo;

public class SmoothComboMonitor implements OnCardUseSubscriber, OnStartBattleSubscriber {

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (SmoothCombo.smoothComboUseTime > 0) {
            if (AbstractDungeon.player.hand.contains(abstractCard)) {
                // 记录第一张卡牌的类型
                SmoothCombo.recordedType = abstractCard.type;
                SmoothCombo.smoothComboUseTime--;
                System.out.println("记录卡牌类型: " + SmoothCombo.recordedType);

                // 触发动作打出抽牌堆顶的牌
                AbstractDungeon.actionManager.addToBottom(new SmoothComboAction());

            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        SmoothCombo.reset();
    }
}
