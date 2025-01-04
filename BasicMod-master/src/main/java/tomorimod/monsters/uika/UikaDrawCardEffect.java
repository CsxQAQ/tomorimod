package tomorimod.monsters.uika;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tomorimod.monsters.uika.UikaIntentCardPatch;

public class UikaDrawCardEffect extends AbstractGameEffect {
    private AbstractMonster monster; // 哪个怪物要“抽”这张卡
    private AbstractCard card;
    private float startX, startY;
    private float targetX, targetY;
    private float startScale, targetScale;
    private float delay; // 延迟时间

    public UikaDrawCardEffect(AbstractMonster monster, AbstractCard card,
                              float startX, float startY,
                              float targetX, float targetY,
                              float startScale, float targetScale,
                              float delay) { // 添加 delay 参数
        this.monster = monster;
        this.card = card;

        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.startScale = startScale;
        this.targetScale = targetScale;

        this.delay = delay; // 设置延迟时间
        this.duration = 0.3f; // 动画持续时间
        this.startingDuration = this.duration;

        // 先把卡牌的初始位置、缩放设好
        card.current_x = startX;
        card.current_y = startY;
        card.drawScale = startScale;
        card.targetDrawScale = startScale;
    }

    @Override
    public void update() {
        // 处理延迟逻辑
        if (this.delay > 0) {
            this.delay -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            return; // 在延迟时间内不执行动画逻辑
        }

        // 随时间推移，卡牌从 startX, startY 逐步移动到 targetX, targetY
        this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        float progress = 1f - (duration / startingDuration);

        // 插值计算位置（线性插值为例）
        float currentX = com.badlogic.gdx.math.MathUtils.lerp(startX, targetX, progress);
        float currentY = com.badlogic.gdx.math.MathUtils.lerp(startY, targetY, progress);
        card.current_x = currentX;
        card.current_y = currentY;

        // 插值计算缩放
        card.drawScale = com.badlogic.gdx.math.MathUtils.lerp(startScale, targetScale, progress);

        // 动画结束
        if (this.duration <= 0.0f) {
            this.isDone = true;
            // 动画结束后，把卡牌“交给”怪物的意图渲染
            UikaIntentCardPatch.AbstractMonsterFieldPatch.intentCard1.set(monster, card);

            // 确保卡牌落到最终位置
            card.current_x = targetX;
            card.target_x = targetX;
            card.current_y = targetY;
            card.target_y = targetY;
            card.drawScale = targetScale;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // 在延迟和动画未结束前，把卡牌渲染出来
        if (!this.isDone && this.delay <= 0) {
            card.render(sb);
        }
    }

    @Override
    public void dispose() {}
}
