package tomorimod.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.VictoryScreen;

public class TomoriTrueVictoryRoom extends AbstractRoom {
    public TomoriTrueVictoryRoom() {
        super();
        this.phase = RoomPhase.INCOMPLETE;
    }

    @Override
    public void onPlayerEntry() {
        // 1. 隐藏Overlay按钮
        AbstractDungeon.overlayMenu.proceedButton.hide();
        // 2. 恢复光标
        GameCursor.hidden = false;
        // 3. 如果需要可淡出音乐
        //    CardCrawlGame.music.fadeOutBGM();
        //    CardCrawlGame.music.fadeOutTempBGM();
        // 4. 直接显示胜利结算
        AbstractDungeon.victoryScreen = new VictoryScreen(null);
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.VICTORY;
        // 5. 标记当前房间为“处理完毕”即可
        this.phase = RoomPhase.COMPLETE;
    }

    @Override
    public void update() {
        super.update();
        // 这里你可以再写点别的逻辑，如果需要的话。
        // 例如检查点击，直接离开游戏之类。
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        // 可以选择不做任何绘制(就一片黑), 或者你自己绘制点东西
    }
}
