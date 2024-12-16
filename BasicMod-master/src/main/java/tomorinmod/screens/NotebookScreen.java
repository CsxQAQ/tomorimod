package tomorinmod.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import tomorinmod.savedata.customdata.HistoryCraftRecords;

import java.util.ArrayList;

import static tomorinmod.BasicMod.imagePath;

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
        historyRecords = HistoryCraftRecords.getInstance().historyCraftRecords;

        currentPage=-1;

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

        // 检查鼠标左键是否点击
        if (InputHelper.justClickedLeft) {
            float mouseX = InputHelper.mX; // 鼠标X坐标
            float mouseY = InputHelper.mY; // 鼠标Y坐标
            handleButtonClick(mouseX, mouseY); // 调用你的按钮点击逻辑
        }

//        if (InputHelper.justClickedLeft) {
//            AbstractDungeon.closeCurrentScreen();
//        }
    }

    private Texture texture;

    public NotebookScreen() {

    }

    private Texture[] displayedImages = new Texture[3];
    private Texture notebookImage=new Texture(imagePath("notebook.png"));

    private ArrayList<ArrayList<String>> historyRecords;

    private final int recordsPerPage=3;
    public static int currentPage=-1;

    private Texture leftButtonTexture=new Texture(imagePath("leftButton.png"));
    private Texture rightButtonTexture=new Texture(imagePath("rightButton.png"));

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(notebookImage, 100, 100, 800, 800);

        if (HistoryCraftRecords.getInstance().historyCraftRecords.isEmpty()) return;

        int totalPages = calculateTotalPages(historyRecords.size(), recordsPerPage);

        // 如果 currentPage 还未初始化，设置为最后一页
        if (currentPage == -1) {
            currentPage = totalPages - 1; // 最新数据所在页
        }

        int[] pageIndices = getPageIndices(currentPage, recordsPerPage, historyRecords.size());
        int startIndex = pageIndices[0];
        int endIndex = pageIndices[1];

        renderPageContent(sb, historyRecords, startIndex, endIndex);

        renderPageButtons(sb, totalPages);
    }

    private int calculateTotalPages(int totalRecords, int recordsPerPage) {
        return (int) Math.ceil((double) totalRecords / recordsPerPage);
    }

    private int[] getPageIndices(int currentPage, int recordsPerPage, int totalRecords) {
        int startIndex = currentPage * recordsPerPage;
        int endIndex = Math.min(startIndex + recordsPerPage, totalRecords);
        return new int[]{startIndex, endIndex};
    }

    private void renderPageContent(SpriteBatch sb, ArrayList<ArrayList<String>> historyRecords, int startIndex, int endIndex) {
        float screenWidth = Settings.WIDTH;
        float screenHeight = Settings.HEIGHT;

        // 计算图片大小与位置
        float imageWidth = screenWidth / 6;
        float imageHeight = imageWidth * 0.75f;
        float ySpacing = screenHeight / 20;
        float startY = screenHeight / 2 + (recordsPerPage - 1) * (imageHeight + ySpacing) / 2;

        for (int recordIndex = startIndex; recordIndex < endIndex; recordIndex++) {
            ArrayList<String> record = historyRecords.get(recordIndex);
            float yPosition = startY - (recordIndex - startIndex) * (imageHeight + ySpacing);

            for (int i = 0; i < 3; i++) {
                if (displayedImages[i] == null || !displayedImages[i].getTextureData().isPrepared()) {
                    displayedImages[i] = new Texture(imagePath("materials/" + record.get(i) + ".png"));
                }

                float xSpacing = screenWidth / 8;
                float xPosition = (i + 1) * xSpacing;

                sb.draw(displayedImages[i], xPosition - imageWidth / 2, yPosition - imageHeight / 2, imageWidth, imageHeight);
            }
        }
    }

    private void renderPageButtons(SpriteBatch sb, int totalPages) {
        float screenWidth = Settings.WIDTH;

        // 按钮尺寸与位置
        float buttonWidth = screenWidth / 12;
        float buttonHeight = buttonWidth * 0.5f;

        float leftButtonX = screenWidth / 4 - buttonWidth / 2;
        float rightButtonX = 3 * screenWidth / 4 - buttonWidth / 2;
        float buttonY = 100;

        sb.draw(leftButtonTexture, leftButtonX, buttonY, buttonWidth, buttonHeight);
        sb.draw(rightButtonTexture, rightButtonX, buttonY, buttonWidth, buttonHeight);

        // 页码显示
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, "Page " + (currentPage + 1) + "/" + totalPages, screenWidth / 2, buttonY + buttonHeight + 20, Color.WHITE);
    }

    public void handleButtonClick(float mouseX, float mouseY) {
        float screenWidth = Settings.WIDTH;

        // 按钮尺寸与位置
        float buttonWidth = screenWidth / 12;
        float buttonHeight = buttonWidth * 0.5f;
        float buttonY = 100;

        float leftButtonX = screenWidth / 4 - buttonWidth / 2;
        float rightButtonX = 3 * screenWidth / 4 - buttonWidth / 2;

        if (mouseX >= leftButtonX && mouseX <= leftButtonX + buttonWidth &&
                mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
            // 左按钮点击
            currentPage = Math.max(currentPage - 1, 0);
        } else if (mouseX >= rightButtonX && mouseX <= rightButtonX + buttonWidth &&
                mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
            // 右按钮点击
            currentPage = Math.min(currentPage + 1, calculateTotalPages(HistoryCraftRecords.getInstance().historyCraftRecords.size(), recordsPerPage) - 1);
        }
    }
}
