package tomorimod.patches;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.CustomBosses;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import tomorimod.configs.TomoriConfig;

import java.util.*;

public class BossGeneratePatch {
    private static final HashMap<String, ArrayList<String>> keys = new HashMap<>();

    public static void addBoss(String dungeon, String bossID, String mapIcon, String mapIconOutline) {
        if (keys.isEmpty() || !keys.containsKey(dungeon)) {
            keys.put(dungeon, new ArrayList<>());
        }
        keys.get(dungeon).add(bossID);
        BaseMod.addBoss(dungeon, bossID, mapIcon, mapIconOutline);
    }

    public static ArrayList<String> getBossKeys(String dungeon) {
        return keys.get(dungeon);
    }

//    @SpirePatch2(clz = CustomBosses.AddBosses.class, method = "Do")
//    public static class removeThisBoss {
//        @SpireInsertPatch(locator = Locator.class)
//        public static void Insert(@ByRef List<String>[] ___customBosses) {
//            if (___customBosses != null &&
//                    !TomoriConfig.config.getBool("modBoss-enabled") &&
//                    ___customBosses[0] != null && !___customBosses[0].isEmpty()) {
//                ___customBosses[0].removeAll(BossGeneratePatch.getBossKeys(AbstractDungeon.id));
//            }
//        }
//
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(BaseMod.class, "getBossIDs");
//                int line = LineFinder.findAllInOrder(ctMethodToPatch, matcher)[0] + 1;
//                return new int[] { line };
//            }
//        }
//    }

    @SpirePatch2(clz = CustomBosses.AddBosses.class, method = "Do")
    public static class removeOtherBoss {
        @SpirePostfixPatch
        public static void postfix() {
            if (TomoriConfig.config.getBool("onlyModBoss-enabled")) {
                List<String> customBosses = BossGeneratePatch.getBossKeys(AbstractDungeon.id);
                if (customBosses != null && !customBosses.isEmpty()) {
                    //AbstractDungeon.bossList = new ArrayList<>();
                    AbstractDungeon.bossList.addAll(customBosses);
                    Collections.shuffle(AbstractDungeon.bossList, new Random(AbstractDungeon.monsterRng.randomLong()));
                }
            }
        }
    }
}