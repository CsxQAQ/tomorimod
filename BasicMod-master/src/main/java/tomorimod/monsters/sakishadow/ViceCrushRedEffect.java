package tomorimod.monsters.sakishadow;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class ViceCrushRedEffect extends AbstractGameEffect {
    private static TextureAtlas.AtlasRegion img;
    private boolean impactHook = false;
    private float x;
    private float x2;
    private float y;
    private float startX;
    private float startX2;
    private float targetX;
    private float targetX2;

    public ViceCrushRedEffect(float x, float y) {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/weightyImpact");
        }

        this.startX = x - 300.0F * Settings.scale - (float)img.packedWidth / 2.0F;
        this.startX2 = x + 300.0F * Settings.scale - (float)img.packedWidth / 2.0F;
        this.targetX = x - 120.0F * Settings.scale - (float)img.packedWidth / 2.0F;
        this.targetX2 = x + 120.0F * Settings.scale - (float)img.packedWidth / 2.0F;
        this.x = this.startX;
        this.x2 = this.startX2;
        this.y = y - (float)img.packedHeight / 2.0F;
        this.scale = 1.1F;
        this.duration = 0.7F;
        this.startingDuration = 0.7F;
        this.rotation = 90.0F;
        this.color = Color.RED.cpy();
        this.color.a = 0.0F;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            CardCrawlGame.sound.playA("ATTACK_MAGIC_FAST_3", -0.4F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.duration < 0.2F) {
            if (!this.impactHook) {
                this.impactHook = true;
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PURPLE));
                CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.MED, true);
            }

            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration * 5.0F);
        } else {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.duration / this.startingDuration);
        }

        this.scale += 1.1F * Gdx.graphics.getDeltaTime();
        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.duration / this.startingDuration);
        this.x2 = Interpolation.fade.apply(this.targetX2, this.startX2, this.duration / this.startingDuration);
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.5F, 0.5F, 0.9F, this.color.a));
        sb.draw(img, this.x, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * this.scale * Settings.scale * 0.5F, this.scale * Settings.scale * (this.duration + 0.8F), this.rotation);
        sb.draw(img, this.x2, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * this.scale * Settings.scale * 0.5F, this.scale * Settings.scale * (this.duration + 0.8F), this.rotation - 180.0F);
        sb.setColor(new Color(0.7F, 0.5F, 0.9F, this.color.a));
        sb.draw(img, this.x, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * this.scale * Settings.scale * 0.75F, this.scale * Settings.scale * (this.duration + 0.8F), this.rotation);
        sb.draw(img, this.x2, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * this.scale * Settings.scale * 0.75F, this.scale * Settings.scale * (this.duration + 0.8F), this.rotation - 180.0F);
        sb.setColor(this.color);
        sb.draw(img, this.x, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * this.scale * Settings.scale, this.scale * Settings.scale * (this.duration + 1.0F), this.rotation);
        sb.draw(img, this.x2, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * this.scale * Settings.scale, this.scale * Settings.scale * (this.duration + 1.0F), this.rotation - 180.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
