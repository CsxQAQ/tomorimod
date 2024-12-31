package tomorimod.monsters.mutumi.friendly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FriendlyMonsterRollMoveAction extends AbstractGameAction {
    private AbstractFriendlyMonster monster;

    public FriendlyMonsterRollMoveAction(AbstractFriendlyMonster monster) {
        this.monster = monster;
    }

    public void update() {
        this.monster.rollMove();
        this.isDone = true;
    }
}
