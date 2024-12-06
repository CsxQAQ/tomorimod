package tomorinmod.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.relics.Pant;
import tomorinmod.relics.TakisGift;

import static tomorinmod.BasicMod.imagePath;

public class TakiReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal(imagePath("badge.png")));

    public TakiReward() {
        super(ICON, "立希的礼物", RewardTypePatch.TAKI_REWARD);
    }

    @Override
    public boolean claimReward() {
        if (!AbstractDungeon.player.hasRelic("TakisGift")) {
            AbstractRelic relic = new TakisGift();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(relic.hb.cX, relic.hb.cY, relic);
            return true;  // 领取成功
        }
        return false;  // 如果已经拥有，则不允许重复领取
    }
}