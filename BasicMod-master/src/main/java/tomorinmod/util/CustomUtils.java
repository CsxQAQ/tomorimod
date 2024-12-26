package tomorinmod.util;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.cards.music.BaseMusicCard;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomUtils {


    public static void addTags(BaseCard aCard, AbstractCard.CardTags tag){
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (aCard.uuid.equals(card.uuid)) {
                card.tags.add(tag);
                break;
            }
        }
    }

    public static String idToName(String id) {
        if (id.contains(":")) {
            return id.split(":")[1]; // 获取 ":" 后的部分
        }
        return id; // 如果没有 ":"，直接返回
    }

    public static void initializeCards(){
        initializeModCards();
        initializeFormCards();
        initializeMusicCards();
        initializeMonmentCards();
    }

    public static HashMap<String, BaseCard> modCardGroup = new HashMap<>();
    public static HashMap<String, BaseFormCard> formCardGroup = new HashMap<>();
    public static HashMap<String, BaseMusicCard> musicCardGroup = new HashMap<>();
    public static HashMap<String, BaseMonmentCard> monmentCardGroup = new HashMap<>();

    /**
     * 初始化所有卡牌
     */
    public static void initializeModCards() {
        // 注意：BaseMod.getCustomCardsToAdd() 返回的是 ArrayList<AbstractCard>
        ArrayList<AbstractCard> modCards = BaseMod.getCustomCardsToAdd();
        for (AbstractCard card : modCards) {
            if (card instanceof BaseCard) {
                BaseCard baseCard = (BaseCard) card;
                // 以 baseCard.cardID 为 Key，baseCard 为 Value
                modCardGroup.put(baseCard.cardID, baseCard);
            }
        }
    }

    /**
     * 将所有 BaseFormCard 类型的卡牌放入 formCardGroup
     */
    public static void initializeFormCards() {
        // 现在 modCardGroup 是 HashMap，需要遍历它的 value
        for (BaseCard baseCard : modCardGroup.values()) {
            if (baseCard instanceof BaseFormCard) {
                BaseFormCard baseFormCard = (BaseFormCard) baseCard;
                // 以 cardID 作为 key
                formCardGroup.put(baseFormCard.cardID, baseFormCard);
            }
        }
    }

    /**
     * 将所有 BaseMusicCard 类型的卡牌放入 musicCardGroup
     */
    public static void initializeMusicCards() {
        for (BaseCard baseCard : modCardGroup.values()) {
            if (baseCard instanceof BaseMusicCard) {
                BaseMusicCard baseMusicCard = (BaseMusicCard) baseCard;
                musicCardGroup.put(baseMusicCard.cardID, baseMusicCard);
            }
        }
    }

    /**
     * 将所有 BaseMonmentCard 类型的卡牌放入 monmentCardGroup
     */
    public static void initializeMonmentCards() {
        for (BaseCard baseCard : modCardGroup.values()) {
            if (baseCard instanceof BaseMonmentCard) {
                BaseMonmentCard baseMonmentCard = (BaseMonmentCard) baseCard;
                monmentCardGroup.put(baseMonmentCard.cardID, baseMonmentCard);
            }
        }
    }


    public static void setRareBanner(CustomCard card){
        card.bannerSmallRegion = ImageMaster.CARD_BANNER_RARE;
        card.bannerLargeRegion = ImageMaster.CARD_BANNER_RARE_L;
        switch (card.type) {
            case ATTACK:
                card.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_RARE;
                card.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
                break;
            case POWER:
                card.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_RARE;
                card.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_RARE_L;
                break;
            default:
                card.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_RARE;
                card.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_RARE_L;
        }

        card.frameMiddleRegion = ImageMaster.CARD_RARE_FRAME_MID;
        card.frameLeftRegion = ImageMaster.CARD_RARE_FRAME_LEFT;
        card.frameRightRegion = ImageMaster.CARD_RARE_FRAME_RIGHT;
        card.frameMiddleLargeRegion = ImageMaster.CARD_RARE_FRAME_MID_L;
        card.frameLeftLargeRegion = ImageMaster.CARD_RARE_FRAME_LEFT_L;
        card.frameRightLargeRegion = ImageMaster.CARD_RARE_FRAME_RIGHT_L;
    }

}
