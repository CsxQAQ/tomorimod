package tomorimod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class SmoothHealthChangeAction extends AbstractGameAction {
    private final AbstractCreature target;
    private final int startHealth;
    private final int targetHealth;
    private final float initialDuration; // 记录总时长

    public SmoothHealthChangeAction(AbstractCreature target, int startHealth, int targetHealth) {
        this.target = target;
        this.startHealth = startHealth;
        this.targetHealth = targetHealth;
        this.duration = 3.0f;          // 想要的过渡时长（3秒）
        this.initialDuration = 3.0f;   // 记录一下初始时长，用于进度计算
        // 可选：根据需求设置 ActionType
        this.actionType = ActionType.HEAL;
    }

    @Override
    public void update() {
        // 如果和你的需求一样，也可以在 duration == initialDuration 时做一些一次性操作
        if (this.duration == this.initialDuration) {
            // 比如播放个声音或特效
            // CardCrawlGame.sound.playA("INTIMIDATE", -0.6F);
        }

        // 每帧减少持续时间
        this.duration -= Gdx.graphics.getDeltaTime();

        // 计算当前进度(0 ~ 1)
        float progress = 1.0f - (this.duration / this.initialDuration);
        // 防止浮点误差导致 > 1 或 < 0
        if (progress < 0f) progress = 0f;
        if (progress > 1f) progress = 1f;

        // 根据进度，计算当前血量
        int newHealth = startHealth + Math.round((targetHealth - startHealth) * progress);

        // 限制在 [1, target.maxHealth] 范围内（也可按你游戏的需求）
        newHealth = Math.max(1, newHealth);
        newHealth = Math.min(newHealth, target.maxHealth);

        // 更新目标生物的血量
        target.currentHealth = newHealth;
        // 通知血条更新
        target.healthBarUpdatedEvent();

        // 当动画时间小于等于 0，就结束 Action
        if (this.duration <= 0f) {
            // 这里也可以再一次确保血量=最终值
            target.currentHealth = targetHealth;
            target.healthBarUpdatedEvent();

            this.isDone = true;
        }
    }
}