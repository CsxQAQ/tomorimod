package tomorimod.screens;

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
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.cards.notshow.utilcards.FailComposition;
import tomorimod.relics.NotebookRelic;
import tomorimod.savedata.customdata.CraftingRecipes;
import tomorimod.savedata.customdata.HistoryCraftRecords;
import tomorimod.util.CustomUtils;

import java.util.*;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class NotebookScreen extends CustomScreen
{
    // --------------------------------------------------------------------------------
    // 一些要点说明：
    // 1. 这里统一用 Settings.scale 来给素材贴图做等比缩放，避免 xScale != yScale 的时候图像被拉伸。
    // 2. 布局位置方面，则可根据需要乘 xScale 或 yScale，或者直接用 Settings.scale，核心是别让素材宽高被拆开来用。
    // 3. 如果想在非常宽屏或非常高屏幕上保持更好的布局，可以考虑先计算贴图原始宽高，然后按比例决定 Notebook 大小。
    // --------------------------------------------------------------------------------

    private static final Map<CraftingRecipes.Material, Map<Integer, Texture>> TEXTURE_MAP = new HashMap<>();

    static {
        // 初始化纹理映射
        for (CraftingRecipes.Material material : CraftingRecipes.Material.values()) {
            Map<Integer, String> levelToPath = new HashMap<>();
            switch (material) {
                case YELLOW:
                case GREEN:
                case RED:
                    levelToPath.put(1, "materials/notebook/" + material.name().toLowerCase() + "_common.png");
                    levelToPath.put(2, "materials/notebook/" + material.name().toLowerCase() + "_uncommon.png");
                    levelToPath.put(3, "materials/notebook/" + material.name().toLowerCase() + "_rare.png");
                    break;
                case AQUARIUMPASS:
                    // 只有2和3档
                    levelToPath.put(2, "materials/notebook/" + material.name().toLowerCase() + "_uncommon.png");
                    levelToPath.put(3, "materials/notebook/" + material.name().toLowerCase() + "_rare.png");
                    break;
            }

            Map<Integer, Texture> levelToTexture = new HashMap<>();
            for (Map.Entry<Integer, String> entry : levelToPath.entrySet()) {
                levelToTexture.put(entry.getKey(), new Texture(imagePath(entry.getValue())));
            }
            TEXTURE_MAP.put(material, levelToTexture);
        }
    }

    public AbstractCard hoveredCard;
    public static Map<CacheKey, AbstractCard> cardCache = new HashMap<>();
    public AbstractCard clickStartedCard;
    public static ArrayList<AbstractCard> currentPageCards = new ArrayList<>();

    private Texture getMaterialTexture(CraftingRecipes.Material material, int level) {
        Map<Integer, Texture> levelMap = TEXTURE_MAP.get(material);
        if (levelMap != null) {
            return levelMap.get(level);
        }
        return null;
    }

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

        currentPage = -1;

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
        if (InputHelper.justClickedRight) {
            AbstractDungeon.closeCurrentScreen();
            AbstractRelic relic = AbstractDungeon.player.getRelic(makeID("NotebookRelic"));
            if (relic != null){
                if(!relic.hb.hovered){
                    NotebookRelic.isOpened = false;
                }
            }
        }
        // 检查鼠标左键是否点击
        if (InputHelper.justClickedLeft) {
            float mouseX = InputHelper.mX; // 鼠标X坐标
            float mouseY = InputHelper.mY; // 鼠标Y坐标
            handleButtonClick(mouseX, mouseY); // 调用你的按钮点击逻辑
        }

        updateCard();
        updateInputLogic();
    }

    private void updateCard(){
        this.hoveredCard = null;
        for(AbstractCard card : currentPageCards){
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

            boolean mouseReleased = InputHelper.justReleasedClickLeft && this.clickStartedCard != null && this.hoveredCard != null;
            boolean controllerPressed = (this.hoveredCard != null && CInputActionSet.select.isJustPressed());

            if (mouseReleased || controllerPressed) {
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

    // --------------------------------------------------------------------------------
    // 统一用 Settings.scale 来等比缩放素材，避免 xScale != yScale 造成拉伸。
    // --------------------------------------------------------------------------------
    private static final float S = Settings.scale;

    // 这些参数用于控制每行记录里的三张材料的摆放和间距
    private static final float X_OFFSET = 225.0f;    // 不再乘 xScale，后面渲染时乘一次 S 就够
    private static final float Y_OFFSET = 265.0f;    // 同上
    private static final float X_INTERVAL = 362.0f;
    private static final float Y_INTERVAL = 190.0f;

    // 材料图片：用 200 * S，保证是方形，不随屏幕宽高比变形
    private static final float MATERIAL_SIZE = 200.0f * S;

    // 每页最多显示 2 条记录
    private final int recordsPerPage = 2;
    public static int currentPage = -1;

    // Notebook 本身贴图如果要保持不变形，也应该自己计算宽高
    // 这里示例：假设原图大约是 1600x900 (宽/高=16:9)，
    // 你可以根据自己 notebook.png 的实际分辨率来改 BASE_WIDTH / BASE_HEIGHT。
    private static final float NOTEBOOK_BASE_WIDTH  = 1600.0f;
    private static final float NOTEBOOK_BASE_HEIGHT = 900.0f;

    // 将 Notebook 尺寸缩放到差不多 80% 屏幕宽度的大小
    private static final float NOTEBOOK_TARGET_WIDTH  = Settings.WIDTH * 0.8f;
    private static final float NOTEBOOK_SCALE        = NOTEBOOK_TARGET_WIDTH / NOTEBOOK_BASE_WIDTH;
    // 最终贴图宽高
    private static final float NOTEBOOK_WIDTH  = NOTEBOOK_BASE_WIDTH  * NOTEBOOK_SCALE;
    private static final float NOTEBOOK_HEIGHT = NOTEBOOK_BASE_HEIGHT * NOTEBOOK_SCALE;
    // Notebook 居中坐标
    private static final float NOTEBOOK_X = (Settings.WIDTH  - NOTEBOOK_WIDTH) / 2f;
    private static final float NOTEBOOK_Y = (Settings.HEIGHT - NOTEBOOK_HEIGHT) / 2f;

    // 按钮贴图仍然用统一的 S 来缩放，不会被拉伸
    private static final float BUTTON_WIDTH_BASE  = 400.0f;
    private static final float BUTTON_HEIGHT_BASE = 200.0f;
    private static final float BUTTON_WIDTH       = BUTTON_WIDTH_BASE  * S;
    private static final float BUTTON_HEIGHT      = BUTTON_HEIGHT_BASE * S;

    // 按钮的位置可根据自己需要排
    // 这里示例：在屏幕底端，左右各摆一个
    private static final float BUTTON_Y       = 100.0f * S;
    private static final float BUTTON_LEFT_X  = (Settings.WIDTH * 0.4f) - BUTTON_WIDTH;
    private static final float BUTTON_RIGHT_X = (Settings.WIDTH * 0.6f);

    // “Page x/y” 的文字偏移
    private static final float BUTTON_FONT_OFFSET = 60.0f * S;

    // 左右按钮贴图
    private Texture leftButtonTexture  = new Texture(imagePath("leftButton.png"));
    private Texture rightButtonTexture = new Texture(imagePath("rightButton.png"));

    private Texture notebookImage = new Texture(imagePath("materials/notebook.png"));
    private Texture[] displayedImages = new Texture[3];
    private ArrayList<CraftingRecipes.Recipe> historyRecords;

    @Override
    public void render(SpriteBatch sb) {
        // 半透明背景
        sb.setColor(new Color(0f, 0f, 0f, 0.8f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(Color.WHITE.cpy());

        // 绘制 Notebook 背景（保证不拉伸：宽高比由你在 NOTEBOOK_BASE_* 里决定）
        sb.draw(notebookImage,
                NOTEBOOK_X,
                NOTEBOOK_Y,
                NOTEBOOK_WIDTH,
                NOTEBOOK_HEIGHT);

        // 如果没有历史记录就不画
        if (HistoryCraftRecords.getInstance().historyCraftRecords.isEmpty()) {
            return;
        }

        int totalPages = calculateTotalPages(historyRecords.size(), recordsPerPage);
        if (currentPage == -1) {
            currentPage = totalPages - 1;
        }
        int[] pageIndices = getPageIndices(currentPage, recordsPerPage, historyRecords.size());
        int startIndex = pageIndices[0];
        int endIndex = pageIndices[1];

        // 绘制本页内容
        renderPageContent(sb, historyRecords, startIndex, endIndex);

        // 绘制翻页按钮和页码
        renderPageButtons(sb, totalPages);
    }

    // 计算总页数
    private int calculateTotalPages(int totalRecords, int recordsPerPage) {
        return (int) Math.ceil((double) totalRecords / recordsPerPage);
    }

    private int[] getPageIndices(int currentPage, int recordsPerPage, int totalRecords) {
        int startIndex = currentPage * recordsPerPage;
        int endIndex = Math.min(startIndex + recordsPerPage, totalRecords);
        return new int[]{startIndex, endIndex};
    }

    public void renderPageContent(SpriteBatch sb,
                                  ArrayList<CraftingRecipes.Recipe> historyRecords,
                                  int startIndex,
                                  int endIndex) {
        currentPageCards.clear();

        for (int recordIndex = startIndex; recordIndex < endIndex; recordIndex++) {
            // 获取当前记录
            CraftingRecipes.Recipe record = historyRecords.get(recordIndex);

            // 计算当前“行”在屏幕上的 y 坐标
            // 这里示例：以 Notebook 顶部为基准往下排
            // 你也可以改成相对于 NOTEBOOK_Y + NOTEBOOK_HEIGHT 来排
            float currentY = (Settings.HEIGHT - NOTEBOOK_Y - 190f * S) - (recordIndex - startIndex) * (MATERIAL_SIZE + Y_INTERVAL * S);

            // 初始 x 坐标
            float currentX = NOTEBOOK_X + X_OFFSET * S;

            // 渲染三张材料图片
            currentX = renderMaterials(sb, record, currentX, currentY);

            // 第四项是卡牌 ID
            String cardID = record.music;
            // 从缓存或卡组中获取（或创建）卡牌
            AbstractCard card = getOrCreateCard(recordIndex, cardID);
            if (card != null) {
                // 设置卡牌的坐标（render需要用到）
                card.current_x = currentX;
                card.target_x  = currentX;
                card.current_y = currentY;
                card.target_y  = currentY;
                // 绘制卡牌
                card.render(sb);

                currentPageCards.add(card);
            }
        }
    }

    private float renderMaterials(SpriteBatch sb, CraftingRecipes.Recipe record, float startX, float centerY) {
        float currentX = startX;
        for (int i = 0; i < 3; i++) {
            // 取到对应材质
            if (displayedImages[i] == null || !displayedImages[i].getTextureData().isPrepared()) {
                displayedImages[i] = getMaterialTexture(record.needs.get(i), record.levels.get(i));
            }
            // 绘制材料图片 (使用 MATERIAL_SIZE x MATERIAL_SIZE => 不会拉伸)
            sb.draw(
                    displayedImages[i],
                    currentX - MATERIAL_SIZE / 2f,
                    centerY - MATERIAL_SIZE / 2f,
                    MATERIAL_SIZE,
                    MATERIAL_SIZE
            );
            // X 坐标右移一段距离
            currentX += (X_INTERVAL * S);
        }
        return currentX;
    }

    private AbstractCard getOrCreateCard(int recordIndex, String cardID) {
        // 先从缓存拿
        CacheKey cacheKey = new CacheKey(recordIndex, cardID);
        AbstractCard card = cardCache.get(cacheKey);
        if (card != null) {
            return card;
        }

        // 缓存没有，就去对应卡组里找
        if ("fail".equals(cardID)) {
            card = new FailComposition();
        } else {
            // 否则来自 CustomUtils.musicCardGroup
            card = CustomUtils.musicCardGroup.get(makeID(cardID)).makeStatEquivalentCopy();
            // 如果是 BaseMusicCard，还需设置特殊属性
            if (card instanceof BaseMusicCard) {
                BaseMusicCard musicCard = (BaseMusicCard) card;
                musicCard.setMusicRarity(BaseMusicCard.getMusicRarityByCost(cardID));
            }
        }

        // 若成功拿到，放入缓存
        if (card != null) {
            cardCache.put(cacheKey, card);
        }
        return card;
    }

    private void renderPageButtons(SpriteBatch sb, int totalPages) {
        // 画左按钮
        sb.draw(leftButtonTexture, BUTTON_LEFT_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        // 画右按钮
        sb.draw(rightButtonTexture, BUTTON_RIGHT_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);

        // 绘制页码
        FontHelper.renderFontCentered(
                sb,
                FontHelper.buttonLabelFont,
                "Page " + (currentPage + 1) + "/" + totalPages,
                Settings.WIDTH / 2.0f,
                BUTTON_Y + BUTTON_HEIGHT + BUTTON_FONT_OFFSET,
                Color.WHITE
        );
    }

    public void handleButtonClick(float mouseX, float mouseY) {
        // 检测左按钮点击
        if (mouseX >= BUTTON_LEFT_X && mouseX <= BUTTON_LEFT_X + BUTTON_WIDTH &&
                mouseY >= BUTTON_Y      && mouseY <= BUTTON_Y      + BUTTON_HEIGHT) {
            currentPage = Math.max(currentPage - 1, 0);
        }
        // 检测右按钮点击
        else if (mouseX >= BUTTON_RIGHT_X && mouseX <= BUTTON_RIGHT_X + BUTTON_WIDTH &&
                mouseY >= BUTTON_Y       && mouseY <= BUTTON_Y       + BUTTON_HEIGHT) {
            int totalRecords = HistoryCraftRecords.getInstance().historyCraftRecords.size();
            int maxPage = calculateTotalPages(totalRecords, recordsPerPage) - 1;
            currentPage = Math.min(currentPage + 1, maxPage);
        }
    }
}

// 用于缓存卡牌
class CacheKey {
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
