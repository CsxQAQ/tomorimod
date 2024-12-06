package tomorinmod.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.relics.Pant;
import tomorinmod.relics.RanasGift;

import static tomorinmod.BasicMod.imagePath;

public class RanaReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal(imagePath("badge.png")));

    public RanaReward() {
        super(ICON, "乐奈的礼物", RewardTypePatch.RANA_REWARD);
    }

    @Override
    public boolean claimReward() {
        if (!AbstractDungeon.player.hasRelic("RanasGift")) {
            AbstractRelic relic = new RanasGift();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(relic.hb.cX, relic.hb.cY, relic);
            return true;  // 领取成功
        }
        return false;  // 如果已经拥有，则不允许重复领取
    }
}