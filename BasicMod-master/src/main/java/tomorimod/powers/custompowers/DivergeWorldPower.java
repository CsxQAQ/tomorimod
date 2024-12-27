package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.powers.BasePower;
import tomorimod.powers.GravityPower;

import static tomorimod.TomoriMod.makeID;

public class DivergeWorldPower extends BasePower {
    public static final String POWER_ID = makeID(DivergeWorldPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DivergeWorldPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }


    @Override
    public void onAttack(DamageInfo info, int damageAmount,AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            if (AbstractDungeon.player.hasPower(makeID("GravityPower"))) {
                for (AbstractPower power : AbstractDungeon.player.powers) {
                    if (power.ID.equals(makeID("GravityPower"))) {
                        ((GravityPower) power).applyEffect();
                        break;
                    }
                }
            }
        }
    }
}
