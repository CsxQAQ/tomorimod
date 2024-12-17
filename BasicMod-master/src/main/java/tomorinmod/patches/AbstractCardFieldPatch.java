package tomorinmod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz= AbstractCard.class,
        method=SpirePatch.CLASS
)
public class AbstractCardFieldPatch {
    public static SpireField<String> material = new SpireField<>(() -> "");
    public static SpireField<Integer> level = new SpireField<>(() -> -1);
    public static SpireField<Texture> ICON = new SpireField<>(() -> null);
}
