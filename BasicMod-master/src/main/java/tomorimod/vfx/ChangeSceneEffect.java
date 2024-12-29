package tomorimod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static tomorimod.TomoriMod.imagePath;

public class ChangeSceneEffect extends AbstractGameEffect {
    private Texture img;
    private Texture img2;
    private Texture img3;
    public float x;
    private boolean right = true;
    private float timer;
    public Color color;
    //private AbstractShaderEffect effect;
    private boolean useShader;

    public ChangeSceneEffect(Texture img) {
        this.color = Color.WHITE.cpy();
        this.renderBehind = true;
        this.img = img;
        this.img2 = ImageMaster.loadImage(imagePath("vfx/Mask.png"));
        this.img3 = ImageMaster.loadImage(imagePath("vfx/Mask2.png"));
        this.x = -Settings.WIDTH * 1.7F;
        this.timer = 0.0F;
    }

//    public void setShader(AbstractShaderEffect effect) {
//        this.effect = effect;
//        this.useShader = true;
//    }

    public void update() {
        this.x = 0.0F;
        if (this.right) {
            if (this.x < 0.0F) {
                this.x += 1500.0F * Gdx.graphics.getDeltaTime() * Settings.scale;
                this.timer -= Gdx.graphics.getDeltaTime();
                if (this.timer < 0.0F) {
                    this.timer += 0.02F;
                }
            } else {
                this.x = 0.0F;
                if (this.useShader) {
                    this.useShader = false;
                    //AbstractDungeon.effectsQueue.add(this.effect);
                }
            }
        } else {
            this.isDone = true;
        }
    }

//    public void end() {
//        if (this.effect != null) {
//            this.effect.end();
//        }
//        this.right = false;
//        this.x = Settings.WIDTH - Settings.WIDTH / 0.8F;
//    }

    public void update(float deltaTime) {}

    public void render(SpriteBatch sb) {
        sb.flush();
        sb.setColor(Color.WHITE.toFloatBits());
        sb.draw(this.img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    private void drawAlphaMask(SpriteBatch sb) {
        Gdx.gl.glColorMask(false, false, false, true);
        sb.setBlendFunction(771, 771);
        if (this.right) {
            sb.draw(this.img2, this.x, 0.0F, Settings.WIDTH / 0.8F, Settings.HEIGHT);
        } else {
            sb.draw(this.img3, this.x, 0.0F, Settings.WIDTH / 0.8F, Settings.HEIGHT);
        }
    }

    private void drawForeground(SpriteBatch sb, int clipX, int clipY, int clipWidth, int clipHeight) {
        Gdx.gl.glColorMask(true, true, true, true);
        sb.setBlendFunction(773, 772);
        Gdx.gl.glEnable(3089);
        Gdx.gl.glScissor(0, clipY, clipWidth, clipHeight);
        sb.setColor(this.color);
        sb.draw(this.img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.flush();
        Gdx.gl.glDisable(3089);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}