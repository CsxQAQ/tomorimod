package tomorinmod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.List;

public class MultiSmoothHealthChangeAction extends AbstractGameAction {
    private final List<AbstractCreature> targets;
    private final List<Integer> startHealths;
    private final List<Integer> targetHealths;
    private final float initialDuration = 1.5f;

    public MultiSmoothHealthChangeAction(List<AbstractCreature> targets,
                                         List<Integer> startHealths,
                                         List<Integer> targetHealths) {
        this.targets = targets;
        this.startHealths = startHealths;
        this.targetHealths = targetHealths;
        this.duration = initialDuration;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        float progress = 1.0f - (duration / initialDuration);
        if (progress < 0f) progress = 0f;
        if (progress > 1f) progress = 1f;

        for (int i = 0; i < targets.size(); i++) {
            AbstractCreature c = targets.get(i);
            int start = startHealths.get(i);
            int end   = targetHealths.get(i);

            int newHealth = start + Math.round((end - start) * progress);
            newHealth = Math.max(1, Math.min(newHealth, c.maxHealth));

            c.currentHealth = newHealth;
            c.healthBarUpdatedEvent();
        }

        if (this.duration <= 0f) {
            for (int i = 0; i < targets.size(); i++) {
                AbstractCreature c = targets.get(i);
                c.currentHealth = targetHealths.get(i);
                c.healthBarUpdatedEvent();
            }
            this.isDone = true;
        }
    }
}
