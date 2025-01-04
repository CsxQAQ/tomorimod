package tomorimod.monsters.uika;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

// 这是示例的“怪物抽牌动画”效果类
public class UikaDrawCardEffect extends AbstractGameEffect {
    private AbstractMonster monster; // 哪个怪物要“抽”这张卡
    private AbstractCard card;
    private float startX, startY;
    private float targetX, targetY;
    private float startScale, targetScale;

    public UikaDrawCardEffect(AbstractMonster monster, AbstractCard card,
                                 float startX, float startY,
                                 float targetX, float targetY,
                                 float startScale, float targetScale) {
        this.monster = monster;
        this.card = card;

        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.startScale = startScale;
        this.targetScale = targetScale;

        this.duration = 0.1f; // 让动画持续 0.5 秒，可自定义
        this.startingDuration = this.duration;

        // 先把卡牌的初始位置、缩放设好
        card.current_x = startX;
        card.current_y = startY;
        card.drawScale = startScale;
        card.targetDrawScale = startScale;
    }

    @Override
    public void update() {
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
            // 这里示例放到 intentCard1，你也可以根据需要放 intentCard2
            UikaIntentCardPatch.AbstractMonsterFieldPatch.intentCard1.set(monster, card);

            // 同时要确保卡牌落到最终位置
            card.current_x = targetX;
            card.target_x=targetX;
            card.current_y = targetY;
            card.target_y=targetY;
            card.drawScale = targetScale;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // 在动画未结束前，也要把卡牌渲染出来
        // 这里是“飞行中的卡牌”
        if (!this.isDone) {
            card.render(sb);
        }
    }

    @Override
    public void dispose() {}
}
