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

    // Note that this can be private and take any parameters you want.
    // When you call openCustomScreen it finds the first method named "open"
    // and calls it with whatever arguments were passed to it.
    private void open(String foo, AbstractCard bar)
    {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        // Call reopen in this example because the basics of
        // setting the current screen are the same across both
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
        // Required if you want to reopen your screen when the settings screen closes
        AbstractDungeon.previousScreen = curScreen();
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
        //texture = new Texture(imagePath("Notebook.png")); // 加载图片
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        //spriteBatch.draw(texture, 100, 100, 1200, 800); // 在指定位置绘制图片
    }


//    public NotebookScreen() {
//        // 初始化卡牌
//        this.strikeCard = CardLibrary.getCard(makeID("Defend")).makeCopy(); // 创建卡牌的副本
//        this.strikeCard.current_x = Settings.WIDTH / 2.0f; // 屏幕中心 X 坐标
//        this.strikeCard.current_y = Settings.HEIGHT / 2.0f; // 屏幕中心 Y 坐标
//        this.strikeCard.drawScale = 1.0f; // 渲染比例
//    }
//
//    @Override
//    public void render(SpriteBatch spriteBatch) {
//        // 渲染背景（可选）
//        spriteBatch.setColor(Color.BLACK);
//        spriteBatch.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);
//
//        // 开始卡牌渲染
//        strikeCard.render(spriteBatch);
//    }


}
