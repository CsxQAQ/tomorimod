package tomorimod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;

public class ExhaustMasterDeckEffect extends AbstractGameEffect {
    private static final float DUR = 1.0F;
    private float x;
    private float y;

    public ExhaustMasterDeckEffect(float x, float y) {
        this.duration = DUR;
        this.x = x;
        this.y = y;
    }

    public void update() {
        if (this.duration == DUR) {
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
            
            for (int i = 0; i < 90; i++) {
                AbstractDungeon.effectsQueue.add(new ExhaustBlurEffect(this.x, this.y));
            }
            for (int i = 0; i < 50; i++) {
                AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.x, this.y));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        // 如果不需要渲染任何东西，可以留空或移除
    }

    public void dispose() {}
}