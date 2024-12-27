package tomorinmod.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import tomorinmod.cards.music.BaseMusicCard;
import tomorinmod.savedata.customdata.HistoryCraftRecords;
import tomorinmod.util.CustomUtils;

import java.util.*;

import static tomorinmod.BasicMod.imagePath;
import static tomorinmod.BasicMod.makeID;

public class NotebookScreen extends CustomScreen
{

    private static final Texture TextureYellowCommon = new Texture(imagePath("materials/notebook/yellow_common.png"));
    private static final Texture TextureGreenCommon = new Texture(imagePath("materials/notebook/green_common.png"));
    private static final Texture TextureRedCommon = new Texture(imagePath("materials/notebook/red_common.png"));
    private static final Texture TextureYellowUncommon = new Texture(imagePath("materials/notebook/yellow_uncommon.png"));
    private static final Texture TextureGreenUncommon = new Texture(imagePath("materials/notebook/green_uncommon.png"));
    private static final Texture TextureRedUncommon = new Texture(imagePath("materials/notebook/red_uncommon.png"));
    private static final Texture TextureYellowRare = new Texture(imagePath("materials/notebook/yellow_rare.png"));
    private static final Texture TextureGreenRare = new Texture(imagePath("materials/notebook/green_rare.png"));
    private static final Texture TextureRedRare = new Texture(imagePath("materials/notebook/red_rare.png"));

    public AbstractCard hoveredCard;
    public static Map<CacheKey, AbstractCard> cardCache = new HashMap<>();
    public AbstractCard clickStartedCard;
    public static ArrayList<AbstractCard> currentPageCards = new ArrayList<>();

    public NotebookScreen() {

    }

    public static void clearNotebookScreenCache(){
        cardCache.clear();
        currentPageCards.clear();
    }


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
        updateCard();
        updateInputLogic();

        //updateCards();

//        if (InputHelper.justClickedLeft) {
//            AbstractDungeon.closeCurrentScreen();
//        }
    }

    private void updateCard(){
        this.hoveredCard = null;
        for(AbstractCard card:currentPageCards){
            card.update();
            card.updateHoverLogic();
            if (card.hb.hovered) {
                this.hoveredCard = card;
            }
        }
    }


    public void updateInputLogic(){

        if (this.hoveredCard != null) {
            CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
            if (InputHelper.justClickedLeft) {
                this.clickStartedCard = this.hoveredCard;
            }

            if (InputHelper.justReleasedClickLeft && this.clickStartedCard != null && this.hoveredCard != null || this.hoveredCard != null && CInputActionSet.select.isJustPressed()) {
                if (Settings.isControllerMode) {
                    this.clickStartedCard = this.hoveredCard;
                }

                InputHelper.justReleasedClickLeft = false;
                CardCrawlGame.cardPopup.open(this.clickStartedCard);
                this.clickStartedCard = null;
            }
        } else {
            this.clickStartedCard = null;
        }
    }
    

    // 屏幕信息
    private static final float SCREEN_WIDTH = Settings.WIDTH;
    private static final float SCREEN_HEIGHT = Settings.HEIGHT;
    private static final float SCALE = Settings.scale;

    // 可调节的布局参数
    private static final float X_OFFSET = 495.0f * SCALE; // 第一张材料图片在屏幕左上角的X偏移
    private static final float Y_OFFSET = SCREEN_HEIGHT - 265.0f * SCALE; // 第一张材料图片在屏幕左上角的Y偏移
    private static final float X_INTERVAL = 310.0f * SCALE; // 材料图片之间的X间隔
    private static final float Y_INTERVAL = 180.0f * SCALE; // 每行记录之间的Y间隔

    // 材料图片和卡牌的渲染大小
    private static final float MATERIAL_WIDTH = 200.0f * SCALE;
    private static final float MATERIAL_HEIGHT = 200.0f * SCALE;

    public void renderPageContent(SpriteBatch sb,
                                  ArrayList<ArrayList<String>> historyRecords,
                                  int startIndex,
                                  int endIndex) {
        currentPageCards.clear();

        for (int recordIndex = startIndex; recordIndex < endIndex; recordIndex++) {
            // 获取当前记录
            ArrayList<String> record = historyRecords.get(recordIndex);

            // 计算当前行的 X/Y 坐标
            float currentY = Y_OFFSET - (recordIndex - startIndex) * (MATERIAL_HEIGHT + Y_INTERVAL);
            float currentX = X_OFFSET;

            // 渲染三张材料图片
            currentX = renderMaterials(sb, record, currentX, currentY);

            // 第四项是卡牌 ID
            String cardID = record.get(3);

            // 从缓存或卡组中获取（或创建）卡牌
            AbstractCard card = getOrCreateCard(recordIndex, cardID);
            if (card != null) {
                // 设置卡牌的坐标（render 需要用到）
                card.current_x = currentX;
                card.target_x = currentX;
                card.current_y = currentY;
                card.target_y = currentY;
                // 绘制卡牌
                card.render(sb);

                currentPageCards.add(card);
            }
        }
    }

    /**
     * 抽取公共的“渲染材料”逻辑
     * @return 渲染完三张材料后最新的 X 坐标
     */
    private float renderMaterials(SpriteBatch sb, ArrayList<String> record, float startX, float startY) {
        float currentX = startX;
        for (int i = 0; i < 3; i++) {
            // record.get(i) 是材料名，取到对应 Texture
            if (displayedImages[i] == null || !displayedImages[i].getTextureData().isPrepared()) {
                displayedImages[i] = getMaterialTexture(record.get(i));
            }
            // 绘制材料图片
            sb.draw(displayedImages[i],
                    currentX - MATERIAL_WIDTH / 2f,
                    startY - MATERIAL_HEIGHT / 2f,
                    MATERIAL_WIDTH,
                    MATERIAL_HEIGHT);
            // X 坐标右移
            currentX += X_INTERVAL;
        }
        return currentX;
    }

    /**
     * 抽取公共的“从缓存或卡组中获取卡牌”的逻辑
     */
    private AbstractCard getOrCreateCard(int recordIndex, String cardID) {
        // 先从缓存拿
        CacheKey cacheKey = new CacheKey(recordIndex, cardID);
        AbstractCard card = cardCache.get(cacheKey);
        if (card != null) {
            return card;
        }

        // 缓存没有，就去对应卡组里找
        if ("fail".equals(cardID)) {
            // “失败”卡牌来自 CustomUtils.modCardGroup
            card = CustomUtils.modCardGroup.get(makeID("FailComposition")).makeStatEquivalentCopy();
                    //findAndCopyCard(cardID, CustomUtils.modCardGroup);
        } else {
            // 否则来自 CustomUtils.musicCardGroup
            card = CustomUtils.musicCardGroup.get(makeID(cardID)).makeStatEquivalentCopy();
                    //findAndCopyCard(cardID, CustomUtils.musicCardGroup);
            // 如果是 BaseMusicCard，还需设置特殊属性
            if (card instanceof BaseMusicCard) {
                BaseMusicCard musicCard = (BaseMusicCard) card;
                musicCard.setMusicRarity(BaseMusicCard.getMusicRarityByCost(cardID));
            }
        }

        // 若成功拿到，放入缓存并添加到 cardsAdded
        if (card != null) {
            cardCache.put(cacheKey, card);
            //cardsAdded.add(card);
        }
        return card;
    }

    private Texture[] displayedImages = new Texture[3];
    private Texture notebookImage=new Texture(imagePath("materials/notebook.png"));

    private ArrayList<ArrayList<String>> historyRecords;

    private final int recordsPerPage=2;
    public static int currentPage=-1;

    private Texture leftButtonTexture=new Texture(imagePath("leftButton.png"));
    private Texture rightButtonTexture=new Texture(imagePath("rightButton.png"));

    // Constants for notebook position and size
    private static final float NOTEBOOK_X = 300.0f*Settings.scale;
    private static final float NOTEBOOK_Y = 300.0f*Settings.scale;
    private static final float NOTEBOOK_WIDTH = Settings.WIDTH-600.0f*Settings.scale;
    private static final float NOTEBOOK_HEIGHT = Settings.HEIGHT-400.0f*Settings.scale;

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(0f, 0f, 0f, 0.8f)); // alpha=0.5f
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(Color.WHITE.cpy());

        sb.draw(notebookImage,
                NOTEBOOK_X ,
                NOTEBOOK_Y ,
                NOTEBOOK_WIDTH ,
                NOTEBOOK_HEIGHT );

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

    public Texture getMaterialTexture(String s){
        int level = Character.getNumericValue(s.charAt(s.length() - 1)); // 获取最后一位并转为数字
        String material = s.substring(0, s.length() - 1); // 去掉最后一位

        switch (material) {
            case "yellow":
                if (level == 1) {
                    return TextureYellowCommon;
                } else if (level == 2) {
                    return TextureYellowUncommon;
                } else if (level == 3) {
                    return TextureYellowRare;
                }
                break;

            case "green":
                if (level == 1) {
                    return TextureGreenCommon;
                } else if (level == 2) {
                    return TextureGreenUncommon;
                } else if (level == 3) {
                    return TextureGreenRare;
                }
                break;

            case "red":
                if (level == 1) {
                    return TextureRedCommon;
                } else if (level == 2) {
                    return TextureRedUncommon;
                } else if (level == 3) {
                    return TextureRedRare;
                }
                break;
        }
        return null;
    }


    // 提取参数为静态常量
    private static final float BUTTON_WIDTH_BASE = 400.0f;      // 按钮基础宽度
    private static final float BUTTON_HEIGHT_BASE = 200.0f;     // 按钮基础高度
    private static final float BUTTON_X_LEFT_BASE = 200.0f;     // 左按钮的X基准
    private static final float BUTTON_X_RIGHT_BASE = 1330.0f;    // 右按钮的X基准
    private static final float BUTTON_Y_BASE = 200.0f;          // 按钮的Y基准
    private static final float BUTTON_FONT_OFFSET_BASE = -150.0f; // 按钮上方字体的偏移基准

    private void renderPageButtons(SpriteBatch sb, int totalPages) {
        // 先将基准值乘以缩放系数，得到最终像素尺寸
        float buttonWidth  = BUTTON_WIDTH_BASE  * Settings.scale;
        float buttonHeight = BUTTON_HEIGHT_BASE * Settings.scale;
        float leftButtonX  = BUTTON_X_LEFT_BASE * Settings.scale;
        float rightButtonX = BUTTON_X_RIGHT_BASE * Settings.scale;
        float buttonY      = BUTTON_Y_BASE      * Settings.scale;
        float fontOffset   = BUTTON_FONT_OFFSET_BASE * Settings.scale;

        // 绘制左按钮
        sb.draw(
                leftButtonTexture,
                leftButtonX,
                buttonY,
                buttonWidth,
                buttonHeight
        );

        // 绘制右按钮
        sb.draw(
                rightButtonTexture,
                rightButtonX,
                buttonY,
                buttonWidth,
                buttonHeight
        );

        // 绘制页码
        FontHelper.renderFontCentered(
                sb,
                FontHelper.buttonLabelFont,
                "Page " + (currentPage + 1) + "/" + totalPages,
                Settings.WIDTH / 2.0f,
                buttonY + buttonHeight + fontOffset,
                Color.WHITE
        );
    }

    public void handleButtonClick(float mouseX, float mouseY) {
        float buttonWidth  = BUTTON_WIDTH_BASE  * Settings.scale;
        float buttonHeight = BUTTON_HEIGHT_BASE * Settings.scale;
        float leftButtonX  = BUTTON_X_LEFT_BASE * Settings.scale;
        float rightButtonX = BUTTON_X_RIGHT_BASE * Settings.scale;
        float buttonY      = BUTTON_Y_BASE      * Settings.scale;

        // 检测左按钮点击
        if (mouseX >= leftButtonX && mouseX <= leftButtonX + buttonWidth &&
                mouseY >= buttonY    && mouseY <= buttonY + buttonHeight) {
            currentPage = Math.max(currentPage - 1, 0);
        }
        // 检测右按钮点击
        else if (mouseX >= rightButtonX && mouseX <= rightButtonX + buttonWidth &&
                mouseY >= buttonY      && mouseY <= buttonY + buttonHeight) {
            int totalRecords = HistoryCraftRecords.getInstance().historyCraftRecords.size();
            int maxPage = calculateTotalPages(totalRecords, recordsPerPage) - 1;
            currentPage = Math.min(currentPage + 1, maxPage);
        }
    }





}

class CacheKey{
    public int position;
    public String cardID;

    public CacheKey(int position, String cardID) {
        this.position = position;
        this.cardID = cardID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return position == cacheKey.position && Objects.equals(cardID, cacheKey.cardID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, cardID);
    }
}
