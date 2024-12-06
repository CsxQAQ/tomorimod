package tomorinmod.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.relics.Pant;
import tomorinmod.rewards.RewardTypePatch;

import static tomorinmod.BasicMod.imagePath;

public class AnonReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal(imagePath("badge.png")));

    public AnonReward(int amount) {
        super(ICON, "爱音的礼物", RewardTypePatch.ANON_REWARD);
    }

    @Override
    public boolean claimReward() {
        // 检查是否已经拥有 "Pant"
        if (!AbstractDungeon.player.hasRelic("Pant")) {
            // 获取名为 "Pant" 的遗物
            AbstractRelic pantRelic = new Pant();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(pantRelic.hb.cX, pantRelic.hb.cY, pantRelic);
            return true;  // 领取成功
        }
        return false;  // 如果已经拥有，则不允许重复领取
    }
}