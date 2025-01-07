package tomorimod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;

public class MonmentEffect extends AbstractGameEffect {
    private static final float DUR = 1.0F;
    private float x;
    private float y;
    private AbstractCard card;

    public MonmentEffect(AbstractCard card,float x, float y) {
        this.duration = DUR;
        this.x = x;
        this.y = y;
        this.card=card;
        card.drawScale = 0.01F;
        card.targetDrawScale = 0.7F;
        card.target_x=x;
        card.target_y=y;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();

        if (this.duration < 0.0F) {
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);

            for (int i = 0; i < 90; i++) {
                AbstractDungeon.effectsQueue.add(new ExhaustBlurEffect(card.current_x, card.current_y));
            }
            for (int i = 0; i < 50; i++) {
                AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(card.current_x, card.current_y));
            }
//            if (AbstractDungeon.player.masterDeck.contains(this.card)) {
//                AbstractDungeon.player.masterDeck.removeCard(this.card);
//            }
            this.isDone = true;
            this.card.shrink();
        }
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }
    }

    public void dispose() {

    }
}