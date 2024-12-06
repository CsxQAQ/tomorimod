package tomorinmod.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.relics.Pant;
import tomorinmod.relics.SoyosGift;

import static tomorinmod.BasicMod.imagePath;

public class SoyoReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal(imagePath("badge.png")));

    public SoyoReward() {
        super(ICON, "素世的礼物", RewardTypePatch.SOYO_REWARD);
    }

    @Override
    public boolean claimReward() {

        if (!AbstractDungeon.player.hasRelic("SoyosGift")) {

            AbstractRelic relic = new SoyosGift();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(relic.hb.cX, relic.hb.cY, relic);
            return true;
        }
        return false;
    }
}