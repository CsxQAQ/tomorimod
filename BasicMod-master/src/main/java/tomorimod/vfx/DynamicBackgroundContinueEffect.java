package tomorimod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

import static tomorimod.TomoriMod.imagePath;

public class DynamicBackgroundContinueEffect extends AbstractGameEffect {

    private static List<Texture> images = new ArrayList<>(); // 存储所有背景图片
    private int currentIndex;          // 当前显示的图片索引
    private float timer;               // 计时器
    private float switchInterval;      // 图片切换的时间间隔（秒）

    private float transitionTime;      // 过渡时间（秒）
    private float transitionTimer;     // 过渡计时器
    private boolean isTransitioning;   // 是否正在过渡
    private int nextIndex;             // 下一张图片的索引

    public DynamicBackgroundContinueEffect(float switchInterval, float transitionTime) {
        this.color = Color.WHITE.cpy();
        this.renderBehind = true;
        this.currentIndex = 0;
        this.switchInterval = switchInterval;
        this.transitionTime = transitionTime;
        this.timer = switchInterval;
        this.transitionTimer = 0.0f;
        this.isTransitioning = false;
        this.nextIndex = (currentIndex + 1) % images.size();
        this.duration = Float.MAX_VALUE; // 持续时间设为无限
        this.isDone = false;
    }

    public static void initializeTexture() {
        for(int i = 1; i <= 10; i++) {
            images.add(new Texture(imagePath("monsters/test/1 (" + i + ").png")));
        }
    }

    @Override
    public void update() {
        timer -= Gdx.graphics.getDeltaTime();

        if (!isTransitioning && timer <= 0.0F) {
            // 开始过渡
            isTransitioning = true;
            transitionTimer = 0.0f;
        }

        if (isTransitioning) {
            transitionTimer += Gdx.graphics.getDeltaTime();
            float alpha = transitionTimer / transitionTime;

            if (alpha >= 1.0f) {
                // 过渡完成
                isTransitioning = false;
                currentIndex = nextIndex;
                nextIndex = (currentIndex + 1) % images.size();
                timer = switchInterval;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (images.isEmpty()) return;

        sb.setColor(Color.WHITE);
        Texture currentImg = images.get(currentIndex);
        sb.draw(currentImg, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);

        if (isTransitioning) {
            Texture nextImg = images.get(nextIndex);
            float alpha = Math.min(transitionTimer / transitionTime, 1.0f);
            sb.setColor(new Color(1, 1, 1, alpha));
            sb.draw(nextImg, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
            sb.setColor(Color.WHITE); // 重置颜色
        }
    }

    @Override
    public void dispose() {

    }
}
