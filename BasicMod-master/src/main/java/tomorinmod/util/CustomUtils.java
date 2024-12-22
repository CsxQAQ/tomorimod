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

import java.util.ArrayList;

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

    public static ArrayList<AbstractCard> getAllModCards() {
        ArrayList<AbstractCard> modCards = BaseMod.getCustomCardsToAdd();
        return modCards;
    }

//    public static ArrayList<AbstractCard> getAllModCards() {
//        ArrayList<AbstractCard> modCards = CardLibrary.getAllCards();
//        return modCards;
//    }
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
