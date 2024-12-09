package tomorinmod.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import tomorinmod.relics.Notebook;
import tomorinmod.savedata.HistoryCraftRecords;

import java.util.ArrayList;

import static tomorinmod.BasicMod.imagePath;
import static tomorinmod.BasicMod.makeID;

public class NotebookScreen extends CustomScreen
{
    public static class Enum
    {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen NOTEBOOK_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen()
    {
        return Enum.NOTEBOOK_SCREEN;
    }


    private void open(String foo, AbstractCard bar)
    {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;

        reopen();
    }

    @Override
    public void reopen()
    {

        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    @Override
    public void openingSettings()
    {
        AbstractDungeon.screen = curScreen();
    }

    @Override
    public void close() {
        AbstractDungeon.isScreenUp = false;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
    }

    @Override
    public void update() {
        if (InputHelper.justClickedLeft) {
            AbstractDungeon.closeCurrentScreen();
        }
    }

    private Texture texture;

    public NotebookScreen() {

    }

    private Texture[] displayedImages = new Texture[3];
    private Texture notebookImaga=new Texture(imagePath("Notebook.png"));

    // render 方法
    @Override
    public void render(SpriteBatch sb) {

        sb.draw(notebookImaga, 100, 100, 800, 800); // 在指定位置绘制图片

        // 如果没有数据，则直接返回
        if (HistoryCraftRecords.getInstance().historyCraftRecords.isEmpty()) return;

        // 获取最新的一条记录
        ArrayList<String> latestRecord = HistoryCraftRecords.getInstance().historyCraftRecords
                .get(HistoryCraftRecords.getInstance().historyCraftRecords.size() - 1);

        // 确保 Texture 被正确加载
        for (int i = 0; i < 3; i++) {
            if (displayedImages[i] == null || !displayedImages[i].getTextureData().isPrepared()) {
                displayedImages[i] = new Texture(imagePath("materials/"+latestRecord.get(i)+".png"));
            }
        }

        // 获取屏幕的宽度和高度
        float screenWidth = Settings.WIDTH;
        float screenHeight = Settings.HEIGHT;

        // 动态计算图片的宽度和高度（假设图片按固定比例缩放）
        float imageWidth = screenWidth / 4;  // 图片占屏幕宽度的1/4
        float imageHeight = imageWidth * 0.75f; // 假设宽高比为4:3

        // 计算图片的位置
        float yPosition = screenHeight / 2 - imageHeight / 2; // 图片垂直居中
        float xSpacing = screenWidth / 8; // 图片间隔
        float[] xPositions = {
                screenWidth / 4 - imageWidth / 2,
                screenWidth / 2 - imageWidth / 2,
                3 * screenWidth / 4 - imageWidth / 2
        };

        //spriteBatch.draw(texture, 100, 100, 1200, 800); // 在指定位置绘制图片
        for (int i = 0; i < 3; i++) {
            sb.draw(displayedImages[i], xPositions[i], yPosition, imageWidth, imageHeight);
        }

    }

}
