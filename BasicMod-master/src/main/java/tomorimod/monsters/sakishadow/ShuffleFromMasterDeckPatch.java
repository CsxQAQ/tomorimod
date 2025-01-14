package tomorimod.monsters.sakishadow;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class ShuffleFromMasterDeckPatch{

    @SpirePatch(
            clz= Soul.class,
            method=SpirePatch.CLASS
    )
    public static class SoulFieldPatch {
        public static SpireField<Boolean> isFromMasterDeck = new SpireField<>(() -> false);
    }

    @SpirePatch(
            clz=Soul.class,
            method = "shuffle",
            paramtypez = {
                    AbstractCard.class, boolean.class
            }
    )
    public static class ShuffleReplacePatch{
        @SpireInsertPatch(
            locator = Locator.class
        )
        public static void insert(Soul __instance, AbstractCard card, boolean isInvisible, @ByRef Vector2[] ___pos,
        float ___MASTER_DECK_X,float ___MASTER_DECK_Y){
            if(SoulFieldPatch.isFromMasterDeck.get(__instance)){
               ___pos[0]=new Vector2(___MASTER_DECK_X,___MASTER_DECK_Y);
               //AbstractDungeon.player.drawPile.group.remove(0);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Soul.class, "pos");

                int[] lines = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
                return new int[]{lines[0]+1};
            }
        }
    }

    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = "update"
    )
    public static class EmptyDeckShuffleActionPatch {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(EmptyDeckShuffleAction __instance) {
            // 如果检测到我们的特殊怪物
            if (isSpecialMonsterPresent()) {
                // 做自定义逻辑（从 masterDeck -> drawPile，且带 Soul 动画）
                doMasterDeckDrawWithSoulEffect(__instance);

                // 跳过原 update() 后续
                return SpireReturn.Return(null);
            }
            // 否则 继续走原方法
            return SpireReturn.Continue();
        }

        // 判断是否有指定怪物
        private static boolean isSpecialMonsterPresent() {
            if (AbstractDungeon.getCurrRoom() == null ||
                    AbstractDungeon.getCurrRoom().monsters == null) {
                return false;
            }
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (makeID("SakiShadowMonster").equals(m.id) && !m.isDeadOrEscaped()) {
                    return true;
                }
            }
            return false;
        }

        // 这里实现把 MasterDeck 里的牌“以Soul动画”转移到 DrawPile
        private static void doMasterDeckDrawWithSoulEffect(EmptyDeckShuffleAction __instance) {
            // 如果你不想真地清空 masterDeck，可根据需求只取几张
            // 这里演示遍历 masterDeck 的每一张卡
            ArrayList<AbstractCard> cardsToMove = new ArrayList<>(AbstractDungeon.player.masterDeck.group);

            for (AbstractCard c : cardsToMove) {
                // 一般不要直接操纵 masterDeck 的原卡，需要复制
                // 如果只是演示，也可 c.makeCopy() 或 c.makeStatEquivalentCopy()
                AbstractCard copyCard = c.makeSameInstanceOf();

                copyCard.untip();
                copyCard.unhover();
                copyCard.darken(true);
                copyCard.shrink(true);

                // 创建新的 Soul
                Soul soul = new Soul();

                SoulFieldPatch.isFromMasterDeck.set(soul,true);

                // 调用我们在 Soul 里新写的 shuffleFromMasterDeck() 方法
                soul.shuffle(copyCard, false);

                // 将 Soul 加入当前房间的 souls 列表，它会自动在 update() 中执行动画
                SoulGroup soulGroup = AbstractDungeon.getCurrRoom().souls;
                try {
                    Field soulsField = SoulGroup.class.getDeclaredField("souls");
                    soulsField.setAccessible(true); // 绕过访问限制
                    ArrayList<Soul> souls = (ArrayList<Soul>) soulsField.get(soulGroup);
                    souls.add(soul); // 修改字段
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                SoulFieldPatch.isFromMasterDeck.set(soul,false);
            }

            AbstractDungeon.player.drawPile.shuffle();
            
            __instance.isDone = true;
        }
    }
}


