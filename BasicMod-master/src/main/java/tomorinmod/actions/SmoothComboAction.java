package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.uncommon.SmoothCombo;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;

public class SmoothComboAction extends AbstractGameAction {
    public SmoothComboAction() {
    }

    private boolean isContinue=true;

    @Override
    public void update() {
        // 检查抽牌堆是否为空
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            isDone = true; // 动作完成
            return;
        }

        if(!isContinue){
            isDone=true;
            return;
        }

        // 从抽牌堆顶取一张牌
        AbstractCard topCard = AbstractDungeon.player.drawPile.getTopCard();
        System.out.println("抽牌堆顶的卡牌: " + topCard.cardID);

        // 如果类型不同，或者抽牌堆为空，停止
        if (topCard.type != SmoothCombo.recordedType) {
            isContinue=false;
        }

        if(isContinue){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    addToBot(new PlayTopCardAction(
                            (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng),
                            false
                    ));

                    addToBot(new SmoothComboAction());
                    isDone = true; // 标记当前动作完成
                }
            });
        }else{
            addToBot(new PlayTopCardAction(
                    (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng),
                    false
            ));
        }

        isDone = true;
    }
}
