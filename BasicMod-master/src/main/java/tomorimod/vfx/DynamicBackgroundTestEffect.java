package tomorimod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

import static tomorimod.TomoriMod.imagePath;

public class DynamicBackgroundTestEffect extends AbstractGameEffect {

    private static List<Texture> images = new ArrayList<>();      // 存储所有背景图片
    private int currentIndex;          // 当前显示的图片索引
    private float timer;               // 计时器
    private float switchInterval;      // 图片切换的时间间隔（秒）
    private static final int STARTPOS=29;
    private static final int ENDPOS=49;

    private static final int IMAGENUM=ENDPOS-STARTPOS+1;

    public DynamicBackgroundTestEffect(float switchInterval) {
        this.color = Color.WHITE.cpy();
        this.renderBehind = true;
        this.currentIndex = 0;
        this.switchInterval = switchInterval;
        this.timer = switchInterval;
        this.duration = switchInterval * IMAGENUM; // 假设有50张图片
        this.isDone = false;
    }

    public static void preloadImages() {
        for(int i = STARTPOS; i <=ENDPOS; i++) {
            String path = imagePath("monsters/test/frame_" + i + ".png");
            try {
                Texture texture = new Texture(path);
                DynamicBackgroundTestEffect.images.add(texture);
                System.out.println("预加载纹理: " + path);
            } catch (Exception e) {
                System.err.println("预加载纹理失败: " + path);
                e.printStackTrace();
            }
        }
    }


    @Override
    public void update() {
        if (isDone) return; // 如果效果已完成，则不再更新

        // 减少计时器
        timer -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (timer <= 0.0F) {
            currentIndex++;
            timer = switchInterval;

            if (currentIndex >= 2*IMAGENUM) {
                // 播放完所有图片，结束效果
                isDone = true;
                //dispose(); // 释放资源
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (images.isEmpty()) return;

        sb.flush();
        sb.setColor(Color.WHITE);
        if (currentIndex < images.size()) {
            Texture currentImg = images.get(currentIndex);
            sb.draw(currentImg, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }else{
            if(2*IMAGENUM-currentIndex-1>0){
                Texture currentImg = images.get(2*IMAGENUM-currentIndex-1);
                sb.draw(currentImg, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
            }
        }
    }

    @Override
    public void dispose() {
//        for (Texture img : images) {
//            img.dispose();
//        }
//        images.clear();
//        System.out.println("释放所有纹理资源");
    }
}
