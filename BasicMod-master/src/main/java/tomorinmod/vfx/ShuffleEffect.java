package tomorinmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;



public class ShuffleEffect extends AbstractGameEffect {
    private final AbstractCard card;
    private final Vector2 startPos;
    private final Vector2 targetPos;
    private final Vector2 currentPos;
    private final float startRotation;
    private final float targetRotation;
    private final float speed;
    private float elapsed;

    public ShuffleEffect(AbstractCard card, Vector2 startPos, Vector2 targetPos, float startRotation, float targetRotation, float speed) {
        this.card = card;
        this.startPos = startPos;
        this.targetPos = targetPos;
        this.currentPos = new Vector2(startPos);
        this.startRotation = startRotation;
        this.targetRotation = targetRotation;
        this.speed = speed;

        this.card.current_x = startPos.x;
        this.card.current_y = startPos.y;
        this.card.target_x = startPos.x;
        this.card.target_y = startPos.y;
        this.card.angle = startRotation;

        this.duration = 1.0f; // 动画持续时间
        this.elapsed = 0.0f;
    }

    public ShuffleEffect(AbstractCard card) {
        // 卡牌
        this.card = card;

        // 起始位置：抽牌堆中心
        this.startPos = new Vector2(AbstractDungeon.player.drawPile.group.isEmpty() ? 0 : AbstractDungeon.player.drawPile.group.get(0).current_x,
                AbstractDungeon.player.drawPile.group.isEmpty() ? 0 : AbstractDungeon.player.drawPile.group.get(0).current_y);

        // 目标位置：弃牌堆中心
        this.targetPos = new Vector2(AbstractDungeon.player.discardPile.group.isEmpty() ? Settings.WIDTH * 0.1f : AbstractDungeon.player.discardPile.group.get(0).current_x,
                AbstractDungeon.player.discardPile.group.isEmpty() ? Settings.HEIGHT * 0.2f : AbstractDungeon.player.discardPile.group.get(0).current_y);

        // 当前初始化为起始位置
        this.currentPos = new Vector2(startPos);

        // 初始旋转角度和目标角度
        this.startRotation = MathUtils.random(-15.0f, 15.0f); // 轻微随机旋转
        this.targetRotation = MathUtils.random(-5.0f, 5.0f);  // 更接近直立

        // 动画速度
        this.speed = 800.0f; // 默认速度

        // 初始化卡牌位置和角度
        this.card.current_x = startPos.x;
        this.card.current_y = startPos.y;
        this.card.target_x = startPos.x;
        this.card.target_y = startPos.y;
        this.card.angle = startRotation;

        // 动画持续时间和已用时间
        this.duration = 1.0f; // 1秒持续时间
        this.elapsed = 0.0f;
    }

    @Override
    public void update() {
        // 动画时间推进
        this.elapsed += Gdx.graphics.getDeltaTime();
        float progress = Math.min(1.0f, elapsed / duration);

        // 位置插值
        this.currentPos.x = MathUtils.lerp(startPos.x, targetPos.x, progress);
        this.currentPos.y = MathUtils.lerp(startPos.y, targetPos.y, progress);

        // 更新卡牌位置
        this.card.current_x = this.currentPos.x;
        this.card.current_y = this.currentPos.y;

        // 角度插值
        this.card.angle = MathUtils.lerp(startRotation, targetRotation, progress);

        // 缩放比例渐变（可选）
        this.card.targetDrawScale = MathUtils.lerp(0.5f, 1.0f, progress);

        // 动画完成
        if (progress >= 1.0f) {
            this.isDone = true;

            // 将卡牌加入到目标牌堆
            if (AbstractDungeon.player != null && AbstractDungeon.player.drawPile != null) {
                AbstractDungeon.player.drawPile.addToTop(this.card);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        this.card.render(sb);
    }

    @Override
    public void dispose() {
        // 资源清理逻辑（如果有需要）
    }
}