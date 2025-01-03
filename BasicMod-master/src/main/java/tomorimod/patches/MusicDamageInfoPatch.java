package tomorimod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class MusicDamageInfoPatch {

    @SpirePatch(
            clz= DamageInfo.class,
            method=SpirePatch.CLASS
    )
    public static class DamageInfoFieldPatch {
        public static SpireField<Boolean> isFromMusicCard = new SpireField<>(() -> false);
    }
}
