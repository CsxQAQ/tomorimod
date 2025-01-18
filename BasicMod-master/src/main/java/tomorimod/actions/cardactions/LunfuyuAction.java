package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.UUID;

public class LunfuyuAction extends AbstractGameAction {
    private AbstractCard card;
    private int increaseAmount;

    public LunfuyuAction(AbstractCard card, int increaseAmount) {
        this.card = card;
        this.increaseAmount = increaseAmount;
    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (!c.uuid.equals(this.card.uuid))
                continue;
            c.misc += this.increaseAmount;
            c.applyPowers();
            c.baseDamage = c.misc;
            c.isDamageModified = false;
        }
        for (AbstractCard c : GetAllInBattleInstances.get(this.card.uuid)) {
            c.misc += this.increaseAmount;
            c.applyPowers();
            c.baseDamage = c.misc;
        }
        isDone=true;
    }
}
