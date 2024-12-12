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

    private static final Texture TextureStone = new Texture(imagePath("materials/" + "Stone.png"));
    private static final Texture TextureBand = new Texture(imagePath("materials/" + "Band.png"));
    private static final Texture TextureWatermelonworm = new Texture(imagePath("materials/" + "Watermelonworm.png"));

    // 缓存拉伸后的宽高和位置
    private static float leftTextureWidth, leftTextureHeight, leftX, leftY;
    private static float topTextureWidth, topTextureHeight, topX, topY;
    private static float rightTextureWidth, rightTextureHeight, rightX, rightY;

    private static int drawCount = 0;

    private static Texture leftTexture = null;
    private static Texture topTexture = null;
    private static Texture rightTexture = null;

    private static boolean initialized = false;

    // 初始化预计算参数
    public static void initialize(float screenWidth, float screenHeight, float characterX, float characterY) {
        float scaleFactor = Math.min(screenWidth / 1920f, screenHeight / 1080f);

        // 计算缩放后的宽高
        leftTextureWidth = TextureStone.getWidth() * scaleFactor / 3;
        leftTextureHeight = TextureStone.getHeight() * scaleFactor / 3;
        topTextureWidth = TextureBand.getWidth() * scaleFactor / 3;
        topTextureHeight = TextureBand.getHeight() * scaleFactor / 3;
        rightTextureWidth = TextureWatermelonworm.getWidth() * scaleFactor / 3;
        rightTextureHeight = TextureWatermelonworm.getHeight() * scaleFactor / 3;

        // 计算位置
        leftX = characterX - leftTextureWidth * 1.2f;
        leftY = characterY + leftTextureHeight * 1.5f;
        topX = characterX - topTextureWidth / 2;
        topY = characterY + topTextureHeight * 2.0f;
        rightX = characterX + rightTextureWidth * 0.2f;
        rightY = characterY + rightTextureHeight * 1.5f;
    }

    public static void clear() {
        drawCount = 0;
        leftTexture = null;
        topTexture = null;
        rightTexture = null;
    }

    @Override
    public void postProcess(SpriteBatch sb, TextureRegion textureRegion, OrthographicCamera camera) {

        if (!initialized) {
            // 获取角色位置和屏幕大小，进行初始化
            float screenWidth = camera.viewportWidth;
            float screenHeight = camera.viewportHeight;
            float characterX = getCharacterX(); // 获取角色X坐标
            float characterY = getCharacterY(); // 获取角色Y坐标
            initialize(screenWidth, screenHeight, characterX, characterY);
            initialized = true; // 确保只初始化一次
        }

        // 绘制原始屏幕内容
        sb.draw(textureRegion, 0, 0);

        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NONE && !SingleRelicViewPopupPreOpenPatch.isRelicWindowOpened) {
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // 支持透明度

            switch (drawCount) {
                case 1:
                    sb.draw(leftTexture, leftX, leftY, leftTextureWidth, leftTextureHeight);
                    break;
                case 2:
                    sb.draw(leftTexture, leftX, leftY, leftTextureWidth, leftTextureHeight);
                    sb.draw(topTexture, topX, topY, topTextureWidth, topTextureHeight);
                    break;
                case 3:
                    sb.draw(leftTexture, leftX, leftY, leftTextureWidth, leftTextureHeight);
                    sb.draw(topTexture, topX, topY, topTextureWidth, topTextureHeight);
                    sb.draw(rightTexture, rightX, rightY, rightTextureWidth, rightTextureHeight);
                    break;
                default:
                    break;
            }
        }
    }

    public static void drawImage(String imageName) {
        drawCount++;
        Texture textureToDraw = null;

        switch (imageName) {
            case "Stone":
                textureToDraw = TextureStone;
                break;
            case "Band":
                textureToDraw = TextureBand;
                break;
            case "Watermelonworm":
                textureToDraw = TextureWatermelonworm;
                break;
            default:
                break;
        }

        switch (drawCount){
            case 1:
                leftTexture=textureToDraw;
                break;
            case 2:
                topTexture=textureToDraw;
                break;
            case 3:
                rightTexture=textureToDraw;
                break;
            default:
                break;
        }
    }
    private float getCharacterX() {
        return AbstractDungeon.player.drawX;
    }

    private float getCharacterY() {
        return AbstractDungeon.player.drawY;
    }

}
