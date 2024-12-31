package tomorimod.monsters.mutumi.friendly;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorimod.patches.FriendlyMonsterPatch;

import java.util.Iterator;

public class SpawnFriendlyMonsterAction extends AbstractGameAction {
    private boolean used;
    private static final float DURATION = 0.1F;
    private AbstractFriendlyMonster m;
    private boolean minion;
    private int targetSlot;
    private boolean useSmartPositioning;

    public SpawnFriendlyMonsterAction(AbstractFriendlyMonster m, boolean isMinion) {
        this(m, isMinion, -99);
        this.useSmartPositioning = true;
    }

    public SpawnFriendlyMonsterAction(AbstractFriendlyMonster m, boolean isMinion, int slot) {
        this.used = false;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = 0.1F;
        this.m = m;
        this.minion = isMinion;
        this.targetSlot = slot;
        this.useSmartPositioning = false;
    }

    public void update() {
        if (!this.used) {
            Iterator var1 = AbstractDungeon.player.relics.iterator();

//            while(var1.hasNext()) {
//                AbstractRelic r = (AbstractRelic)var1.next();
//                r.onSpawnMonster(this.m);
//            }

            this.m.init();
            this.m.applyPowers();

            FriendlyMonsterPatch.FriendlyMonsterFieidPatch.friendlyMonster.set(AbstractDungeon.getCurrRoom(),this.m);

            //AbstractDungeon.getCurrRoom().monsters.addMonster(this.targetSlot, this.m);


            this.m.showHealthBar();
            if (ModHelper.isModEnabled("Lethality")) {
                this.addToBot(new ApplyPowerAction(this.m, this.m, new StrengthPower(this.m, 3), 3));
            }

            if (ModHelper.isModEnabled("Time Dilation")) {
                this.addToBot(new ApplyPowerAction(this.m, this.m, new SlowPower(this.m, 0)));
            }

            if (this.minion) {
                this.addToTop(new ApplyPowerAction(this.m, this.m, new MinionPower(this.m)));
            }

            this.used = true;
        }

        this.tickDuration();
    }
}
