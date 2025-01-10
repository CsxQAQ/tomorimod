package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.monsters.sakishadow.ShuffleFromMasterDeckPatch;
import tomorimod.patches.RecyclePatch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public class RecycleAction extends AbstractGameAction {



    public RecycleAction() {

    }

    public void update() {
        // 获取原始的 exhaustPile.group
        ArrayList<AbstractCard> exhaustGroup = AbstractDungeon.player.exhaustPile.group;
        // 使用 Iterator 遍历
        Iterator<AbstractCard> iterator = exhaustGroup.iterator();

        while (iterator.hasNext()) {
            AbstractCard c = iterator.next();
            if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS) {
                // 移除当前卡牌
                AbstractCard copyCard = c.makeStatEquivalentCopy();

                copyCard.untip();
                copyCard.unhover();
                copyCard.darken(true);
                copyCard.shrink(true);

                Soul soul = new Soul();

                RecyclePatch.SoulFieldPatch.isFromExhaustPile.set(soul, true);

                soul.shuffle(copyCard, false);

                SoulGroup soulGroup = AbstractDungeon.getCurrRoom().souls;
                try {
                    Field soulsField = SoulGroup.class.getDeclaredField("souls");
                    soulsField.setAccessible(true);
                    ArrayList<Soul> souls = (ArrayList<Soul>) soulsField.get(soulGroup);
                    souls.add(soul);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                RecyclePatch.SoulFieldPatch.isFromExhaustPile.set(soul, false);

                iterator.remove();
            }
        }

        // 打乱抽牌堆
        AbstractDungeon.player.drawPile.shuffle();

        isDone = true;
    }


}
