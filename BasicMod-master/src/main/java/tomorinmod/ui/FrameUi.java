package tomorinmod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import tomorinmod.screens.Renderable;

import java.util.ArrayList;
import java.util.List;

import static tomorinmod.BasicMod.imagePath;

public class FrameUi implements Renderable {

    public static final Texture TextureFrame = new Texture(imagePath("materials/" + "frame.png"));

    private static final float SIZE = 120.0F;
    private static final float X_OFFSET = 115.0F;
    private static final float Y_OFFSET = 400.0F;
    private static final float INTERVAL = 200.0F;
    private static final int NUM_UI_ELEMENTS = 3;

    private float rotationAngle = 0.0F;

    private List<Hitbox> hitboxes;

    private FrameUi(){
        hitboxes = new ArrayList<>();
        for (int i = 0; i < NUM_UI_ELEMENTS; i++) {
            float x = X_OFFSET * Settings.scale;
            float y = (Y_OFFSET + i * INTERVAL) * Settings.scale;
            float width = SIZE * Settings.scale;
            float height = SIZE * Settings.scale;
            hitboxes.add(new Hitbox(x, y, width, height));
        }
    }

    private static FrameUi instance;

    public static FrameUi getInstance() {
        if (instance == null) {
            instance = new FrameUi();
        }
        return instance;
    }

    @Override
    public void render(SpriteBatch sb) {
        for (int i = 0; i < NUM_UI_ELEMENTS; i++) {
            float x = X_OFFSET * Settings.scale;
            float y = (Y_OFFSET + i * INTERVAL) * Settings.scale;
            drawFrame(sb, x, y); // 绘制 UI 元素
            hitboxes.get(i).render(sb); // 渲染 hitbox 边框（可选，调试用）
        }

        update(0.001f);
    }

    private void drawFrame(SpriteBatch sb, float x, float y) {
        float originX = SIZE / 2.0F;
        float originY = SIZE / 2.0F;
        sb.draw(
                TextureFrame,
                x, y, // 左下角坐标
                originX, originY, // 旋转的原点（相对坐标）
                SIZE, SIZE, // 纹理的宽高
                Settings.scale, Settings.scale, // 缩放比例
                rotationAngle, // 旋转角度
                0, 0, // 纹理坐标（起点）
                TextureFrame.getWidth(), TextureFrame.getHeight(), // 纹理的实际宽高
                false, false // 翻转标志（水平和垂直）
        );
    }

    public void update(float deltaTime) {
        // 更新旋转角度 (每秒旋转一定的角度，假设是90度/秒)
        rotationAngle += 90.0F * deltaTime;
        if (rotationAngle >= 360.0F) {
            rotationAngle -= 360.0F; // 保持角度在 0~360 之间
        }

        for (int i = 0; i < NUM_UI_ELEMENTS; i++) {
            Hitbox hb = hitboxes.get(i);
            hb.update();
            if (hb.hovered) {
                float tipX = hb.x + hb.width + 10.0F * Settings.scale; // 在 hitbox 右侧 10px
                float tipY = hb.y + (hb.height / 2.0F); // 保持 Y 轴居中
                TipHelper.renderGenericTip(tipX, tipY, "素材区", "获得3点 #y素材 以创作 #y音乐 。");
            }
        }
    }

}
