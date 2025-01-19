package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import tomorimod.util.PlayerUtils;


public class RemoveEventPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeSpecialOneTimeEventList"
    )
    public static class RemoveAbstractDungeonEvent{
        @SpirePostfixPatch
        public static void postfix(AbstractDungeon __instance) {
            if (PlayerUtils.isTomori()){
                AbstractDungeon.specialOneTimeEventList.remove("N'loth"); //避免吃了我的遗物
            }
        }
    }

    @SpirePatch(
            clz = TheCity.class,
            method = "initializeEventList"
    )
    public static class RemoveTheCityEvent{
        @SpirePostfixPatch
        public static void postfix(TheCity __instance) {
            if(PlayerUtils.isTomori()){
                AbstractDungeon.eventList.remove("Drug Dealer");
            }
        }
    }

    @SpirePatch(
            clz = Exordium.class,
            method = "initializeShrineList"
    )
    public static class RemoveMatchAndKeepExordiumEvent{
        @SpirePostfixPatch
        public static void postfix(Exordium __instance) {
            if(PlayerUtils.isTomori()){
                AbstractDungeon.shrineList.remove("Match and Keep!");
            }
        }
    }

    @SpirePatch(
            clz = TheCity.class,
            method = "initializeShrineList"
    )
    public static class RemoveMatchAndKeepTheCityEvent{
        @SpirePostfixPatch
        public static void postfix(TheCity __instance) {
            if(PlayerUtils.isTomori()){
                AbstractDungeon.shrineList.remove("Match and Keep!");
            }
        }
    }

    @SpirePatch(
            clz = TheBeyond.class,
            method = "initializeShrineList"
    )
    public static class RemoveMatchAndKeepTheBeyondEvent{
        @SpirePostfixPatch
        public static void postfix(TheBeyond __instance) {
            if(PlayerUtils.isTomori()){
                AbstractDungeon.shrineList.remove("Match and Keep!");
            }
        }
    }


}