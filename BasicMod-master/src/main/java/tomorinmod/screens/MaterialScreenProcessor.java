package tomorinmod.screens;

import basemod.interfaces.ScreenPostProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.patches.SingleRelicViewPopupPreOpenPatch;

import static tomorinmod.BasicMod.imagePath;

public class MaterialScreenProcessor implements ScreenPostProcessor {

    private Texture leftTexture=new Texture(imagePath("materials/" +"Stone.png"));;  // 用于左上角的图片
    private Texture topTexture=new Texture(imagePath("materials/" + "Band.png"));;   // 用于上方的图片
    private Texture rightTexture=new Texture(imagePath("materials/" +"Watermelonworm.png"));; // 用于右上角的图片

    @Override
    public void postProcess(SpriteBatch sb, TextureRegion textureRegion, OrthographicCamera camera) {
        // 绘制原始屏幕内容
        sb.draw(textureRegion, 0, 0);

        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NONE&&!SingleRelicViewPopupPreOpenPatch.isRelicWindowOpened){
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // 支持透明度

            // 获取屏幕的宽度和高度
            float screenWidth = camera.viewportWidth;
            float screenHeight = camera.viewportHeight;

            // 假设可以通过某种方式获取角色的中心位置（x, y）
            float characterX = getCharacterX(); // 角色中心的 X 坐标
            float characterY = getCharacterY(); // 角色中心的 Y 坐标

            // 获取每张图片的宽高
            float leftTextureWidth = leftTexture.getWidth();
            float leftTextureHeight = leftTexture.getHeight();
            float topTextureWidth = topTexture.getWidth();
            float topTextureHeight = topTexture.getHeight();
            float rightTextureWidth = rightTexture.getWidth();
            float rightTextureHeight = rightTexture.getHeight();

            // 调整缩放因子（根据需要）
            float scaleFactor = Math.min(screenWidth / 1920f, screenHeight / 1080f);
            leftTextureWidth *= scaleFactor/3;
            leftTextureHeight *= scaleFactor/3;
            topTextureWidth *= scaleFactor/3;
            topTextureHeight *= scaleFactor/3;
            rightTextureWidth *= scaleFactor/3;
            rightTextureHeight *= scaleFactor/3;

            // 计算三张图片的相对位置
            float leftX = characterX - leftTextureWidth * 1.2f; // 左上：稍偏左
            float leftY = characterY + leftTextureHeight * 1.5f; // 偏上

            float topX = characterX - topTextureWidth / 2; // 上方：水平居中
            float topY = characterY + topTextureHeight * 2.0f; // 偏上更多

            float rightX = characterX + rightTextureWidth * 0.2f; // 右上：稍偏右
            float rightY = characterY + rightTextureHeight * 1.5f; // 偏上

            // 绘制三张图片
            sb.draw(leftTexture, leftX, leftY, leftTextureWidth, leftTextureHeight);
            sb.draw(topTexture, topX, topY, topTextureWidth, topTextureHeight);
            sb.draw(rightTexture, rightX, rightY, rightTextureWidth, rightTextureHeight);
        }
    }



    // 伪代码：获取角色中心 X 坐标
    private float getCharacterX() {
        return AbstractDungeon.player.drawX;
    }

    // 伪代码：获取角色中心 Y 坐标
    private float getCharacterY() {
        return AbstractDungeon.player.drawY;
    }
}
