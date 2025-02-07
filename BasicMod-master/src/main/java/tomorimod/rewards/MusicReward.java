package tomorimod.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import tomorimod.cards.music.BaseMusicCard;

import java.util.ArrayList;
import java.util.Collections;

import static tomorimod.TomoriMod.imagePath;

public class MusicReward extends CustomReward {
    private static final Texture ICON = new Texture(imagePath("music_reward.png"));

    public String cardId;

    public MusicReward(String cardId) {
        super(ICON, "将一张音乐牌加入牌组", RewardTypePatch.MUSIC_REWARD);
        this.cardId=cardId;
    }

    @Override
    public boolean claimReward() {
        AbstractCard card=CardLibrary.getCard(cardId).makeStatEquivalentCopy();
        AbstractDungeon.cardRewardScreen.open(new ArrayList<>(Collections.singletonList(card)), this, "选择一张卡牌");
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        return false;

//        AbstractCard card = CardLibrary.getCard(cardId).makeCopy();
//        //下面的过程不涉及makeStatEquivalentCopy，不涉及new，有点反直觉
//        if (card != null) {
//            if(card instanceof BaseMusicCard){
//                BaseMusicCard baseMusicCard=(BaseMusicCard)card;
//                BaseMusicCard.MusicRarity musicRarity= BaseMusicCard.getMusicRarityByCost(baseMusicCard.cardID);
//                if(musicRarity!=null){
//                    baseMusicCard.setMusicRarity(musicRarity);
//                    //baseMusicCard.setBanner();
//                    //baseMusicCard.setDisplayRarity(baseMusicCard.rarity);
//
//                    AbstractDungeon.cardRewardScreen.open(new ArrayList<>(Collections.singletonList(baseMusicCard)), this, "选择一张卡牌");
//                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
//                }
//            }
//        }
//        return false;
    }
}
