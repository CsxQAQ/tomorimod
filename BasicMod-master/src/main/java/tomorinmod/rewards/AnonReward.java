package tomorinmod.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.relics.AnonsGift;
import tomorinmod.relics.Pant;
import tomorinmod.rewards.RewardTypePatch;

import static tomorinmod.BasicMod.imagePath;

public class AnonReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal(imagePath("badge.png")));

    public AnonReward() {
        super(ICON, "爱音的礼物", RewardTypePatch.ANON_REWARD);
    }

    @Override
    public boolean claimReward() {
        if (!AbstractDungeon.player.hasRelic("AnonsGift")) {
            AbstractRelic relic = new AnonsGift();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(relic.hb.cX, relic.hb.cY, relic);
            return true;  // 领取成功
        }
        return false;  // 如果已经拥有，则不允许重复领取
    }
}