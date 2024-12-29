package tomorimod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.monsters.taki.RanaMonster;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderHealth",
        paramtypez = { SpriteBatch.class }
)
public class RanaHealthPatch {

    // 使用 ThreadLocal 存储原始 hb 属性，确保线程安全
    private static final ThreadLocal<Float> originalCX = new ThreadLocal<>();
    private static final ThreadLocal<Float> originalCY = new ThreadLocal<>();
    private static final ThreadLocal<Float> originalWidth = new ThreadLocal<>();
    private static final ThreadLocal<Float> originalHeight = new ThreadLocal<>();
    private static final ThreadLocal<Float> originalYOffset = new ThreadLocal<>();

    // 前缀补丁：在 renderHealth 方法执行前修改 hb 的值

    //FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, TEXT[0], this.hb.cX,
    //y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y - 1.0F * Settings.scale, this.hbTextColor);

    @SpirePrefixPatch
    public static void Prefix(AbstractCreature __instance,SpriteBatch sb, float ___hbYOffset) {
        if(__instance instanceof RanaMonster){
            // 保存原始 hb 属性
            originalCX.set(__instance.hb.cX);
            originalCY.set(__instance.hb.cY);
            originalWidth.set(__instance.hb.width);
            originalHeight.set(__instance.hb.height);
            originalYOffset.set(___hbYOffset);

            // 修改 hb 属性
            __instance.hb.cX = __instance.drawX;
            __instance.hb.cY = __instance.drawY;
            __instance.hb.width = 230.0f*Settings.scale;
            __instance.hb.height = 0.0F;
            ___hbYOffset = 0.0F;
        }

    }

    // 后缀补丁：在 renderHealth 方法执行后恢复 hb 的原始值
    @SpirePostfixPatch
    public static void Postfix(AbstractCreature __instance,SpriteBatch sb,float ___hbYOffset) {
        if(__instance instanceof RanaMonster){
            // 恢复原始 hb 属性
            __instance.hb.cX = originalCX.get();
            __instance.hb.cY = originalCY.get();
            __instance.hb.width = originalWidth.get();
            __instance.hb.height = originalHeight.get();
            ___hbYOffset = originalYOffset.get();

            // 清除 ThreadLocal 中存储的值
            originalCX.remove();
            originalCY.remove();
            originalWidth.remove();
            originalHeight.remove();
            originalYOffset.remove();
        }

    }
}