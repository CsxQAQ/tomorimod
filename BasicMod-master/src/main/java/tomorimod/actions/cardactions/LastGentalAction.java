package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.actions.ApplyShineAction;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.util.PlayerUtils;

import static tomorimod.TomoriMod.makeID;

public class LastGentalAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public LastGentalAction(AbstractPlayer p) {
        this.p = p;
    }

    @Override
    public void update() {

        int gravityAmount = PlayerUtils.getPowerNum(makeID("GravityPower"));
        int shineAmount = PlayerUtils.getPowerNum(makeID("ShinePower"));

        if (AbstractDungeon.player.hasPower(makeID("MygoTogetherPower"))) {
            if(gravityAmount>0&&shineAmount>0){
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        int tmp=gravityAmount;
                        AbstractDungeon.player.getPower(makeID("GravityPower")).amount=shineAmount;
                        AbstractDungeon.player.getPower(makeID("ShinePower")).amount=tmp;
                        isDone=true;
                    }
                });
            }
            else if (shineAmount > 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, ShinePower.POWER_ID));
                addToBot(new ApplyGravityAction(shineAmount));
            }
            else if (gravityAmount > 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, GravityPower.POWER_ID));
                addToBot(new ApplyShineAction(gravityAmount));
            }

        } else {
            if (shineAmount > 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, ShinePower.POWER_ID));
                addToBot(new ApplyGravityAction(shineAmount));
            }
            else if (gravityAmount > 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, GravityPower.POWER_ID));
                addToBot(new ApplyShineAction(gravityAmount));
            }
        }

        isDone = true;
    }
}
