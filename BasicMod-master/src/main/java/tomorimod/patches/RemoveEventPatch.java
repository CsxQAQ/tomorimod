package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;


public class RemoveEventPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeSpecialOneTimeEventList"
    )
    public static class RemoveAbstractDungeonEvent{
        @SpirePostfixPatch
        public static void postfix(AbstractDungeon __instance) {
            AbstractDungeon.specialOneTimeEventList.remove("N'loth"); //避免吃了我的遗物
        }
    }

    @SpirePatch(
            clz = TheCity.class,
            method = "initializeEventList"
    )
    public static class RemoveTheCityEvent{
        @SpirePostfixPatch
        public static void postfix(TheCity __instance) {
            AbstractDungeon.eventList.remove("Drug Dealer");
        }
    }


}