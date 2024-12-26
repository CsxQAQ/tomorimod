package tomorinmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static tomorinmod.BasicMod.imagePath;

public class StarDustEffect extends AbstractGameEffect {

    // -- 参数可灵活调整 --
    private static final float FALL_DISTANCE = 400.0F*Settings.scale; // 星星起始位置比目标高多少
    private static final float DURATION = 2.0F;        // 总下落时长

    // 下落的图
    private Texture img;

    // 坐标
    private float startX, startY;
    private float targetX, targetY;
    private float currentX, currentY;

    // 旋转相关
    private float rotationAngle = 0.0F;   // 当前旋转角度
    private float rotationSpeed = 720.0F; // 旋转速度(度/秒)，这里示例 720 度/秒

    // 动画时长
    private float startingDuration;

    // 用于存放尾巴粒子
    private List<TailParticle> particles = new ArrayList<>();

    // ================ 构造 =====================
    public StarDustEffect(float x, float y) {
        this.img = ImageMaster.loadImage(imagePath("vfx/star.png"));

        // 设置星星的起始位置、目标位置
        this.startX = x-30.0f*Settings.scale;
        this.startY = y + FALL_DISTANCE;
        this.targetX = x-30.0f*Settings.scale;
        this.targetY = y+FALL_DISTANCE/2.0f;

        // 当前坐标
        this.currentX = startX;
        this.currentY = startY;

        // 动画时长
        this.duration = DURATION;
        this.startingDuration = DURATION;

        // 颜色
        this.color = Color.WHITE.cpy();
        this.color.a = 1.0f; // 不透明度
    }

    @Override
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        this.duration -= delta;

        // 计算进度[0 ~ 1]
        float progress = 1.0f - (this.duration / this.startingDuration);

        // ============ 1) 非匀速下落 ============
        // 使用 pow2Out，让下落前期较快，后期减速
        this.currentY = Interpolation.pow2Out.apply(startY, targetY, progress);
        this.currentX = Interpolation.pow2Out.apply(startX, targetX, progress);

        // ============ 2) 旋转 ============
        this.rotationAngle += rotationSpeed * delta;

        // ============ 3) 生成尾巴粒子 ============
        // 每帧都创建一个粒子，粒子会在后续逐渐消失
        TailParticle p = new TailParticle(currentX, currentY, this.color.cpy());
        particles.add(p);

        // 更新尾巴粒子
        for (TailParticle tp : particles) {
            tp.update(delta);
        }

        // 移除已消失的粒子
        Iterator<TailParticle> it = particles.iterator();
        while (it.hasNext()) {
            TailParticle tp = it.next();
            if (tp.isDone) {
                it.remove();
            }
        }

        // 当动画结束后，将 isDone 置为 true
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // 先画尾巴粒子
        for (TailParticle tp : particles) {
            tp.render(sb);
        }

        // 再画星星本体
        sb.setColor(this.color);
        float halfW = this.img.getWidth() / 2f;
        float halfH = this.img.getHeight() / 2f;
        sb.draw(
                this.img,
                this.currentX - halfW,
                this.currentY - halfH,
                halfW, // 旋转中心X
                halfH, // 旋转中心Y
                this.img.getWidth(),
                this.img.getHeight(),
                1.0f,          // scaleX
                1.0f,          // scaleY
                this.rotationAngle, // rotation angle
                0,             // srcX
                0,             // srcY
                this.img.getWidth(),
                this.img.getHeight(),
                false,
                false
        );
    }

    @Override
    public void dispose() {
        // 通常不在这里手动 dispose 贴图，或得看你对资源管理的策略
    }

    // ============ 内部类：用来实现尾巴粒子 ============
    // 你可以改成多个贴图随机使用，或者用小星星贴图都行
    // ============ 内部类：用来实现尾巴粒子 ============
    private static class TailParticle {
        private static final float LIFE_TIME = 0.5f; // 尾巴粒子寿命(秒)
        private float x;
        private float y;
        private float duration;  // 剩余寿命
        private boolean isDone = false;
        private Color color;

        // 这里使用同一张星星贴图
        private static Texture tailImg = ImageMaster.loadImage(imagePath("vfx/star.png"));

        TailParticle(float x, float y, Color c) {
            this.x = x;
            this.y = y;
            this.duration = LIFE_TIME;
            // 初始化颜色(包含透明度)
            this.color = c;
        }

        void update(float delta) {
            this.duration -= delta;
            if (duration < 0) {
                this.isDone = true;
                return;
            }
            // 随着生命衰减让透明度变小
            float progress = 1.0f - (duration / LIFE_TIME);
            // 从 1 -> 0
            this.color.a = 1.0f - progress;
        }

        void render(SpriteBatch sb) {
            if (isDone) return;

            sb.setColor(this.color);
            float halfW = tailImg.getWidth() / 2f;
            float halfH = tailImg.getHeight() / 2f;
            // 根据剩余生命可逐渐缩放
            // 这里演示从 0.5 ~ 1.0，可自行调整
            float scale = 0.5f + (this.duration / LIFE_TIME) * 0.5f; // 0.5 ~ 1.0

            sb.draw(
                    tailImg,
                    this.x - halfW,
                    this.y - halfH,
                    halfW,
                    halfH,
                    tailImg.getWidth(),
                    tailImg.getHeight(),
                    scale,
                    scale,
                    0.0f,  // 不旋转尾巴粒子
                    0,
                    0,
                    tailImg.getWidth(),
                    tailImg.getHeight(),
                    false,
                    false
            );
        }
    }
}