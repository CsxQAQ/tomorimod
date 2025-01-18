package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.patches.RecyclePatch;
import tomorimod.util.PlayerUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public class NewRecycleAction extends AbstractGameAction {
    public NewRecycleAction() {

    }

    public void update() {
        // 获取原始的 exhaustPile.group
        ArrayList<AbstractCard> exhaustGroup = AbstractDungeon.player.exhaustPile.group;
        // 使用 Iterator 遍历
        Iterator<AbstractCard> iterator = exhaustGroup.iterator();

        while (iterator.hasNext()) {
            AbstractCard copyCard = iterator.next();
            if (copyCard.type != AbstractCard.CardType.CURSE && copyCard.type != AbstractCard.CardType.STATUS) {
                copyCard.unfadeOut();
                copyCard.unhover();
                //copyCard.stopGlowing();

                AbstractDungeon.player.drawPile.addToTop(copyCard);
                iterator.remove();
            }
        }

        AbstractDungeon.player.drawPile.shuffle();
        isDone=true;
    }



}
