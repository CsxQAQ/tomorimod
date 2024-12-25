package tomorinmod.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
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

    private static final Texture TextureStoneCommon = new Texture(imagePath("materials/notebook/stone_common.png"));
    private static final Texture TextureBandCommon = new Texture(imagePath("materials/notebook/band_common.png"));
    private static final Texture TextureFlowerCommon = new Texture(imagePath("materials/notebook/flower_common.png"));
    private static final Texture TextureStoneUncommon = new Texture(imagePath("materials/notebook/stone_uncommon.png"));
    private static final Texture TextureBandUncommon = new Texture(imagePath("materials/notebook/band_uncommon.png"));
    private static final Texture TextureFlowerUncommon = new Texture(imagePath("materials/notebook/flower_uncommon.png"));
    private static final Texture TextureStoneRare = new Texture(imagePath("materials/notebook/stone_rare.png"));
    private static final Texture TextureBandRare = new Texture(imagePath("materials/notebook/band_rare.png"));
    private static final Texture TextureFlowerRare = new Texture(imagePath("materials/notebook/flower_rare.png"));

    private AbstractCard hoveredCard;
    Map<CacheKey, BaseMusicCard> cardCache = new HashMap<>();
    private AbstractCard clickStartedCard;
    private ArrayList<AbstractCard> cardsAdded=new ArrayList<>();

    public NotebookScreen() {

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
        for(AbstractCard card:cardsAdded){
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
    private static final float X_OFFSET = 200.0f * SCALE; // 第一张材料图片在屏幕左上角的X偏移
    private static final float Y_OFFSET = SCREEN_HEIGHT - 250.0f * SCALE; // 第一张材料图片在屏幕左上角的Y偏移
    private static final float X_INTERVAL = 300.0f * SCALE; // 材料图片之间的X间隔
    private static final float Y_INTERVAL = 10.0f * SCALE; // 每行记录之间的Y间隔
    private static final float CARD_INTERVAL_ODD = 100.0f * SCALE; // 第三张材料和卡牌之间的额外间隔（奇数）
    private static final float CARD_INTERVAL_EVEN = 500.0f * SCALE; // 第三张材料和卡牌之间的额外间隔（偶数）

    // 材料图片和卡牌的渲染大小
    private static final float MATERIAL_WIDTH = 300.0f * SCALE;
    private static final float MATERIAL_HEIGHT = 300.0f * SCALE;

    public void renderPageContent(SpriteBatch sb, ArrayList<ArrayList<String>> historyRecords, int startIndex, int endIndex) {

        for (int recordIndex = startIndex; recordIndex < endIndex; recordIndex++) {
            ArrayList<String> record = historyRecords.get(recordIndex);

            // 根据 recordIndex 调整当前行的 Y 坐标，让不同记录在不同行显示
            float currentY = Y_OFFSET - (recordIndex - startIndex) * (MATERIAL_HEIGHT + Y_INTERVAL);
            float currentX = X_OFFSET;

            // ======= 渲染三张材料图片 =======
            for (int i = 0; i < 3; i++) {
                // record.get(i) 是材料的名字，需要根据名字取到 Texture
                if (displayedImages[i] == null || !displayedImages[i].getTextureData().isPrepared()) {
                    displayedImages[i] = getMaterialTexture(record.get(i));
                }
                // 在 (currentX, currentY) 位置画出材料图片
                sb.draw(displayedImages[i],
                        currentX - MATERIAL_WIDTH / 2f,
                        currentY - MATERIAL_HEIGHT / 2f,
                        MATERIAL_WIDTH,
                        MATERIAL_HEIGHT);

                // 为下一张材料图片移动 X 坐标
                currentX += X_INTERVAL;
            }

            // ======= 渲染卡牌 =======
            String cardID = record.get(3);

            if(!cardID.equals("fail")){
                // 从缓存中获取卡牌副本
                BaseMusicCard card = cardCache.get(new CacheKey(recordIndex, cardID));
                if (card == null) {
                    for (BaseMusicCard musicCard : CustomUtils.musicCardGroup) {
                        if (musicCard.cardID.equals(makeID(cardID))) {
                            card = musicCard.makeStatEquivalentCopy();
                            break;
                        }
                    }
                    card.setMusicRarity(BaseMusicCard.getMusicRarityByCost(cardID));
                    if(recordIndex%2==0){
                        currentX += CARD_INTERVAL_ODD;
                    }else{
                        currentX += CARD_INTERVAL_EVEN;
                    }
                    card.current_x = currentX;
                    card.target_x = currentX;
                    card.current_y = currentY;
                    card.target_y = currentY;
                    cardsAdded.add(card);
                    cardCache.put(new CacheKey(recordIndex, cardID), card);
                }
                card.render(sb);
            }
        }
    }




//    private void updateCards() {
//        this.hoveredCard = null;
//        ArrayList<AbstractCard> cards = this.visibleCards.group;
//
//        for(int i = 0; i < cards.size(); ++i) {
//            cards.get(i).update();
//            cards.get(i).updateHoverLogic();
//            if (cards.get(i).hb.hovered) {
//                this.hoveredCard = cards.get(i);
//            }
//        }
//    }

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
