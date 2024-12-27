package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;
import java.util.UUID;

public class BitianbanzouAction extends AbstractGameAction {

    private UUID uuid;

    public BitianbanzouAction(UUID uuid) {
        this.uuid=uuid;
    }

    public void update() {
        Iterator<AbstractCard> var1 = AbstractDungeon.player.masterDeck.group.iterator();

        while (var1.hasNext()) {
            AbstractCard abstractCard = var1.next();
            if (abstractCard.uuid.equals(this.uuid)) {
                abstractCard.misc += 5;
            }
        }

        this.isDone = true;
    }
}
