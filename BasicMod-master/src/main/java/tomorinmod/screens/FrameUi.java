package tomorinmod.screens;

import basemod.interfaces.ScreenPostProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.core.Settings;

import static tomorinmod.BasicMod.imagePath;

public class FrameUi implements Renderable{

    public static final Texture TextureFrame = new Texture(imagePath("materials/" + "frame.png"));

    private static final float SIZE = 120.0F;
    private static final float X_OFFSET = 115.0F;
    private static final float Y_OFFSET = 400.0F;
    private static final float INTERVAL = 200.0F;

    private float rotationAngle = 0.0F; // 当前旋转角度

    private FrameUi(){
    }

    private static FrameUi instance;

    public static FrameUi getInstance() {
        if (instance == null) {
            instance = new FrameUi();
        }
        return instance;

    }

//    @Override
//    public void render(SpriteBatch sb) {
//        sb.draw(TextureFrame, X0FFSET*Settings.scale, Y0FFSET*Settings.scale,SIZE,SIZE);
//        sb.draw(TextureFrame, X0FFSET*Settings.scale, (Y0FFSET+INTERVAL)*Settings.scale,SIZE,SIZE);
//        sb.draw(TextureFrame, X0FFSET*Settings.scale, (Y0FFSET+2*INTERVAL)*Settings.scale,SIZE,SIZE);
//    }

    @Override
    public void render(SpriteBatch sb) {
        float originX = SIZE / 2.0F;
        float originY = SIZE / 2.0F;

        sb.draw(
                TextureFrame,
                X_OFFSET * Settings.scale, Y_OFFSET * Settings.scale, // 左下角坐标
                originX, originY,                                     // 原点位置
                SIZE, SIZE,                                           // 宽高
                Settings.scale, Settings.scale,                      // 缩放
                rotationAngle,                                        // 旋转角度
                0, 0,                                                // 起始纹理坐标
                TextureFrame.getWidth(), TextureFrame.getHeight(),   // 纹理宽高
                false, false                                         // 翻转
        );

        sb.draw(
                TextureFrame,
                X_OFFSET * Settings.scale, (Y_OFFSET + INTERVAL) * Settings.scale,
                originX, originY,
                SIZE, SIZE,
                Settings.scale, Settings.scale,
                rotationAngle,
                0, 0,
                TextureFrame.getWidth(), TextureFrame.getHeight(),
                false, false
        );

        sb.draw(
                TextureFrame,
                X_OFFSET * Settings.scale, (Y_OFFSET + 2 * INTERVAL) * Settings.scale,
                originX, originY,
                SIZE, SIZE,
                Settings.scale, Settings.scale,
                rotationAngle,
                0, 0,
                TextureFrame.getWidth(), TextureFrame.getHeight(),
                false, false
        );
        update(0.001f);
    }

    public void update(float deltaTime) {
        // 更新旋转角度 (每秒旋转一定的角度，假设是90度/秒)
        rotationAngle += 90.0F * deltaTime;
        if (rotationAngle >= 360.0F) {
            rotationAngle -= 360.0F; // 保持角度在 0~360 之间
        }
    }

}
