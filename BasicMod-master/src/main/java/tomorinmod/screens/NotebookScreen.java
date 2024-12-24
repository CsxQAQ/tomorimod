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
import tomorinmod.cards.music.BaseMusicCard;
import tomorinmod.savedata.customdata.HistoryCraftRecords;
import tomorinmod.util.CustomUtils;

import java.util.ArrayList;

import static tomorinmod.BasicMod.imagePath;
import static tomorinmod.BasicMod.makeID;

public class NotebookScreen extends CustomScreen
{

    private static final Texture TextureStoneCommon = new Texture(imagePath("materials/notebook/stone_common.png"));
    private static final Texture TextureBandCommon = new Texture(imagePath("materials/notebook/band_common.png"));
    private static final Texture TextureFlowerCommon = new Texture(imagePath("materials/notebook/flower_common.png"));
    private static final Texture TextureStoneUncommon = new Texture(imagePath("materials/notebook/stone_uncommon.png"));
    private static final Texture TextureBandUncommon = new Texture(imagePath("materials/notebook/band_uncommon.png"));
    private static final Texture TextureFlowerUncommon = new Texture(imagePath("materials/notebook/flower_uncommon.png"));
    private static final Texture TextureStoneRare = new Texture(imagePath("materials/notebook/stone_rare.png"));
    private static final Texture TextureBandRare = new Texture(imagePath("materials/notebook/band_rare.png"));
    private static final Texture TextureFlowerRare = new Texture(imagePath("materials/notebook/flower_rare.png"));

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
    private Texture notebookImage=new Texture(imagePath("materials/notebook.png"));

    private ArrayList<ArrayList<String>> historyRecords;

    private final int recordsPerPage=3;
    public static int currentPage=-1;

    private Texture leftButtonTexture=new Texture(imagePath("leftButton.png"));
    private Texture rightButtonTexture=new Texture(imagePath("rightButton.png"));

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(notebookImage, 100.0f*Settings.scale, 100.0f*Settings.scale,
                1700.0f*Settings.scale, 1150.0f*Settings.scale);

        if (HistoryCraftRecords.getInstance().historyCraftRecords.isEmpty()) return;

        int totalPages = calculateTotalPages(historyRecords.size(), recordsPerPage);

        if (currentPage == -1) {
            currentPage = totalPages - 1;
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
        // 1. 获取屏幕信息
        float screenWidth  = Settings.WIDTH;
        float screenHeight = Settings.HEIGHT;
        float scale        = Settings.scale;

        // 2. 可调节的布局参数
        float x_offset    = 200.0f * scale;                   // 第一张材料图片在屏幕左上角的X偏移
        float y_offset    = screenHeight - 300.0f * scale;    // 第一张材料图片在屏幕左上角的Y偏移
        float x_INTERVAL  = 300.0f * scale;                   // 材料图片之间的X间隔
        float y_INTERVAL  = 100.0f * scale;                   // 每行记录之间的Y间隔
        float INTERVAL    = 300.0f * scale;                   // 第三张材料和卡牌之间的额外间隔

        // 3. 材料图片和卡牌的渲染大小
        float materialWidth   = 300.0f * scale;
        float materialHeight  = 300.0f * scale;

        for (int recordIndex = startIndex; recordIndex < endIndex; recordIndex++) {
            ArrayList<String> record = historyRecords.get(recordIndex);

            // 根据 recordIndex 调整当前行的 Y 坐标，让不同记录在不同行显示
            float currentY = y_offset - (recordIndex - startIndex) * (materialHeight + y_INTERVAL);
            float currentX = x_offset;

            // ======= 4. 渲染三张材料图片 =======
            for (int i = 0; i < 3; i++) {
                // record.get(i) 是材料的名字，需要根据名字取到 Texture
                if (displayedImages[i] == null || !displayedImages[i].getTextureData().isPrepared()) {
                    displayedImages[i] = getMaterialTexture(record.get(i));
                }
                // 在 (currentX, currentY) 位置画出材料图片
                sb.draw(displayedImages[i],
                        currentX - materialWidth / 2f,
                        currentY - materialHeight / 2f,
                        materialWidth,
                        materialHeight);

                // 为下一张材料图片移动 X 坐标
                currentX += x_INTERVAL;
            }

            // ======= 5. 渲染卡牌 =======
            // 取卡牌ID，并根据ID拿到卡牌对象
            String cardID = record.get(3);

            BaseMusicCard card = null;
            for(BaseMusicCard musicCard: CustomUtils.musicCardGroup){
                if(musicCard.cardID.equals(makeID(cardID))){
                    card=musicCard.makeStatEquivalentCopy();
                    break;
                }
            }
            if (card != null) {
                card.setMusicRarity(BaseMusicCard.getMusicRarityByCost(cardID));
                // 材料与卡牌之间空出一个额外的 INTERVAL
                currentX += INTERVAL;

                // 设置卡牌渲染坐标
                card.current_x  = currentX;
                card.current_y  = currentY;
                //card.drawScale  = cardDrawScale;  // 控制卡牌大小

                // 重点：调用官方方法
                //card.renderInLibrary(sb);
            }
        }
    }

    public Texture getMaterialTexture(String s){
        int level = Character.getNumericValue(s.charAt(s.length() - 1)); // 获取最后一位并转为数字
        String material = s.substring(0, s.length() - 1); // 去掉最后一位

        switch (material) {
            case "stone":
                if (level == 1) {
                    return TextureStoneCommon;
                } else if (level == 2) {
                    return TextureStoneUncommon;
                } else if (level == 3) {
                    return TextureStoneRare;
                }
                break;

            case "band":
                if (level == 1) {
                    return TextureBandCommon;
                } else if (level == 2) {
                    return TextureBandUncommon;
                } else if (level == 3) {
                    return TextureBandRare;
                }
                break;

            case "flower":
                if (level == 1) {
                    return TextureFlowerCommon;
                } else if (level == 2) {
                    return TextureFlowerUncommon;
                } else if (level == 3) {
                    return TextureFlowerRare;
                }
                break;
        }
        return null;
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
