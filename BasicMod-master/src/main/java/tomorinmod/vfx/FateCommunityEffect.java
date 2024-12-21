package tomorinmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FateCommunityEffect extends AbstractGameEffect {
    private AbstractCreature target;

    public FateCommunityEffect(AbstractCreature target) {
        this.duration = 3.0F;
        this.color = new Color(1.0F, 1.0F, 0.8F, 0.5F);
        this.target = target;
    }

    public void update() {
        if (this.duration == 3.0F) {
            CardCrawlGame.sound.playA("INTIMIDATE", -0.6F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > 1.5F) {
            this.color.a = Interpolation.pow5In.apply(0.5F, 0.0F, (this.duration - 1.5F) / 1.5F);
        } else {
            this.color.a = Interpolation.exp10In.apply(0.0F, 0.5F, this.duration / 1.5F);
        }

        if (this.duration < 0.0F) {
            this.color.a = 0.0F;
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);

        if (!target.isDead && !target.isDying) {
            float fixedWidth = 500.0F * Settings.scale; // 使用 Settings.scale 调整宽度
            float fixedHeight = 1800.0F * Settings.scale; // 使用 Settings.scale 调整高度

            sb.draw(ImageMaster.SPOTLIGHT_VFX,
                    target.drawX - fixedWidth / 2.0F, // 让特效中心和怪物位置对齐
                    target.drawY - fixedHeight / 2.0F,
                    fixedWidth,
                    fixedHeight);
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
