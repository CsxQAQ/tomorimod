package tomorimod.monitors.card;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.cards.customcards.ConveyFeeling;
import tomorimod.monitors.BaseMonitor;
import tomorimod.tags.CustomTags;

import java.util.ArrayList;

public class ShortTermGoalMonitor extends BaseMonitor implements OnPlayerTurnStartSubscriber {

    @Override
    public void receiveOnPlayerTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;

        // 移动抽牌堆里带“原生”tag的卡
        ArrayList<AbstractCard> cardsInDrawPile = new ArrayList<>(p.drawPile.group);
        for (AbstractCard c : cardsInDrawPile) {
            if (c.hasTag(CustomTags.SHORTTERMGOAL)) {
                p.drawPile.moveToHand(c);

            }
        }

        // 移动弃牌堆里带“原生”tag的卡
        ArrayList<AbstractCard> cardsInDiscardPile = new ArrayList<>(p.discardPile.group);
        for (AbstractCard c : cardsInDiscardPile) {
            if (c.hasTag(CustomTags.SHORTTERMGOAL)) {
                p.discardPile.moveToHand(c);
            }
        }
    }

}
