package tomorimod.monsters.sakishadow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sun.security.provider.ConfigFile;

public class SakiShadowFadeVFX {
    // 用于处理渐入渐出


    private final float fadeInDuration = 4.0f;
    private float fadeInTimer = fadeInDuration;


    private final float fadeOutDuration = 0.5f;
    private float fadeOutTimer = fadeOutDuration;

    private SakiShadowMonster monster;
    public SakiShadowFadeVFX(SakiShadowMonster monster){
        this.monster=monster;
    }

    public void handleFadeIn() {
        fadeInTimer -= Gdx.graphics.getDeltaTime();
        if (fadeInTimer < 0f) {
            fadeInTimer = 0f;
            monster.isFadingIn = false;
        }
        float progress = 1.0f - (fadeInTimer / fadeInDuration);
        monster.alpha = Interpolation.fade.apply(0f, 1f, progress);
        monster.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, monster.alpha));

        // 若渐入结束，把 alpha 设回 1
        if (!monster.isFadingIn) {
            monster.alpha = 1.0f;
            monster.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, monster.alpha));
        }
    }

    public void handleRebirthFadeOut() {
        fadeOutTimer -= Gdx.graphics.getDeltaTime();
        if (fadeOutTimer < 0f) {
            fadeOutTimer = 0f;
            monster.isRebirth = false;
        }
        monster.alpha = fadeOutTimer / fadeOutDuration;
        monster.tint.changeColor(new Color(1.0F, 1.0F, 1.0F, monster.alpha));

        if (!monster.isRebirth) {
            // 减到 0 之后，再把怪移到屏幕外
            monster.drawX = -1000.0f;
            monster.drawY = -1000.0f;
        }

    }
}
