package tomorimod.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import com.megacrit.cardcrawl.dungeons.TheEnding;

import basemod.helpers.CardBorderGlowManager;
import basemod.BaseMod;
import com.megacrit.cardcrawl.screens.DeathScreen;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class SakiShadowEvent extends AbstractImageEvent {
    // 1) 定义 ID（应与你的 eventStrings.json 里对应）
    public static final String ID = makeID(SakiShadowEvent.class.getSimpleName());;
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String IMG = imagePath("events/Anon.png");
    // 3) 枚举，用于区分不同事件阶段（如果需要多阶段则可自行扩展）
    private enum CurScreen {
        INTRO, // 初始对话
        DONE   // 完成，等待退出
    }

    private CurScreen screen = CurScreen.INTRO;

    // 4) 构造方法
    public SakiShadowEvent() {
        // 父类 AbstractImageEvent 的构造参数：
        // super(事件标题, 事件描述（第一段）, 事件图片路径)
        super(NAME, DESCRIPTIONS[0], IMG);

        // 检查玩家是否拥有 3 把钥匙（红、绿、蓝）
        boolean hasAllKeys = hasAllKeys();

        // 添加选项 1：直面内心
        // 如果没有 3 把钥匙，则该选项灰掉
        if (hasAllKeys) {
            this.imageEventText.setDialogOption(OPTIONS[0]); // 直面内心（可选）
        } else {
            this.imageEventText.setDialogOption(OPTIONS[0], true); // 直面内心（灰色，无法点击）
        }

        // 添加选项 2：逃避
        this.imageEventText.setDialogOption(OPTIONS[1]); // 逃避
    }

    // 5) 按钮响应
    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    // 按下选项 1：直面内心
                    case 0:
                        // 如果有 3 把钥匙，则跳转到 TheEnding（腐化之心）
                        if (hasAllKeys()) {
                            goToTheEnding();
                        }
                        // 如果没有，则什么都不做（因为选项是灰掉的，理论上点不了）
                        break;

                    // 按下选项 2：逃避
                    case 1:
                        // 结束本次游戏。这里演示一种最简单的做法：直接触发“结算”。
                        // 或者你也可以自定义一些演出，例如先弹一个 fade out，再返回主菜单等。
                        endTheRun();
                        break;
                }
                // 按下按钮之后，将屏幕标记为 DONE
                this.screen = CurScreen.DONE;
                break;

            case DONE:
                // 如果事件已经到最后阶段，再次点击按钮则会打开地图离开
                openMap();
                break;
        }
    }

    private boolean hasAllKeys() {
        return Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey;
    }

    private void goToTheEnding() {
        this.roomEventText.clear();
        this.hasFocus = false;
        this.roomEventText.hide();
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.DOOR_UNLOCK;
        CardCrawlGame.mainMenuScreen.doorUnlockScreen.open(true);
    }

    private void endTheRun() {
        AbstractDungeon.player.isDying = true;
        this.hasFocus = false;
        this.roomEventText.hide();
        AbstractDungeon.player.isDead = true;
        AbstractDungeon.deathScreen = new DeathScreen(null);
    }
}
