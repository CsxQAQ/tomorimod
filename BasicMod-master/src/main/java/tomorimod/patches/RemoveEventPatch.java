package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "initializeSpecialOneTimeEventList"
)
public class RemoveEventPatch {
    @SpirePostfixPatch
    public static void removeNloth(AbstractDungeon __instance) {
        AbstractDungeon.specialOneTimeEventList.remove("N'loth"); //避免吃了我的遗物
    }
}