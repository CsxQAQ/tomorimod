package tomorimod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static tomorimod.TomoriMod.imagePath;

public class ChangeSceneEffect extends AbstractGameEffect {
    private Texture img;
    public float x;
    private boolean right = true;
    private float timer;
    public Color color;

    public ChangeSceneEffect(Texture img) {
        this.color = Color.WHITE.cpy();
        this.renderBehind = true;
        this.img = img;
        AbstractDungeon.scene =new TheEndingScene();
    }


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
            }
        } else {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb) {
        sb.flush();
        sb.setColor(Color.WHITE.toFloatBits());
        sb.draw(this.img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }


    public void dispose() {}
}