package tomorimod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.monsters.mutumi.friendly.AbstractFriendlyMonster;


public class FriendlyMonsterPatch {

    @SpirePatch2(
            clz = AbstractRoom.class,
            method=SpirePatch.CLASS
    )
    public static class FriendlyMonsterFieidPatch {
        public static SpireField<AbstractFriendlyMonster> friendlyMonster = new SpireField<>(() -> null);
    }

    @SpirePatch2(
            clz = AbstractRoom.class,
            method = "update"
    )
    public static class FriendlyMonsterUpdatePatch{
        @SpireInsertPatch(
                loc=279
        )
        public static void setRelicWindowOpenedTure(AbstractRoom __instance) {
            if(FriendlyMonsterFieidPatch.friendlyMonster.get(__instance)!=null){
                FriendlyMonsterFieidPatch.friendlyMonster.get(__instance).update();
            }
        }
    }

    @SpirePatch2(
            clz = AbstractRoom.class,
            method = "render"
    )
    public static class FriendlyMonsterRenderPatch{
        @SpireInsertPatch(
                rloc=19
        )
        public static void setRelicWindowOpenedTure(AbstractRoom __instance, SpriteBatch sb) {
            if(FriendlyMonsterFieidPatch.friendlyMonster.get(__instance)!=null){
                FriendlyMonsterFieidPatch.friendlyMonster.get(__instance).render(sb);
            }
        }
    }
}
