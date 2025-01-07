package tomorimod.patches;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.CustomBosses;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CtBehavior;
import tomorimod.character.Tomori;
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

    @SpirePatch2(clz = CustomBosses.AddBosses.class, method = "Do")
    public static class removeOtherBoss {
        @SpirePostfixPatch
        public static void postfix() {
            if (AbstractDungeon.player instanceof Tomori||TomoriConfig.config.getBool("onlyModBoss-enabled")) {
                List<String> customBosses = BossGeneratePatch.getBossKeys(AbstractDungeon.id);
                if (customBosses != null && !customBosses.isEmpty()) {
                    if(!AbstractDungeon.id.equals("TheBeyond")){
                        for(String boss:customBosses){
                            AbstractDungeon.bossList.add(0,boss);
                        }
                    }else{ //boss数剩余2才会触发双boss
                        AbstractDungeon.bossList.remove(0);
                        AbstractDungeon.bossList.remove(0);
                        Collections.shuffle(customBosses, new Random(AbstractDungeon.monsterRng.randomLong()));
                        for(String boss:customBosses){
                            AbstractDungeon.bossList.add(0,boss);
                        }
                    }


                }
            }
        }
    }

//    @SpirePatch(clz= MonsterRoomBoss.class,method = "onPlayerEntry")
//    public static class fixBugPatch{
//        @SpirePostfixPatch
//        public static void postfix(MonsterRoomBoss __instance){
//            AbstractDungeon.bossList.add(CardCrawlGame.dungeon.getBoss().monsters.get(0).id);
//        }
//    }

}