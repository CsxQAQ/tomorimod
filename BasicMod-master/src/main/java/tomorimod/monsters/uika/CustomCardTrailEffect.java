package tomorimod.monsters.uika;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CustomCardTrailEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.5F;
    private static final float DUR_DIV_2 = 0.25F;
    private static TextureAtlas.AtlasRegion img = null;
    private static final int W = 12;
    private static final int W_DIV_2 = 6;
    private static final float SCALE_MULTI;
    private float x;
    private float y;

    public CustomCardTrailEffect(Color color) {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/blurDot2");
        }

        this.renderBehind = false;
        this.color = color;
    }

    public void init(float x, float y) {
        this.duration = 0.5F;
        this.startingDuration = 0.5F;
        this.x = x - 6.0F;
        this.y = y - 6.0F;
        this.scale = 0.01F;
        this.isDone = false;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.25F) {
            this.scale = this.duration * SCALE_MULTI;
        } else {
            this.scale = (this.duration - 0.25F) * SCALE_MULTI;
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 0.18F, this.duration / 0.5F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(img, this.x, this.y, 6.0F, 6.0F, 12.0F, 12.0F, this.scale, this.scale, 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }

    public void reset() {
    }

    static {
        SCALE_MULTI = Settings.scale * 22.0F;
    }
}
