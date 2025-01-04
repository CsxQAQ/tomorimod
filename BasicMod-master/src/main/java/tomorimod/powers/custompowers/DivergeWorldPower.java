package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.powers.BasePower;
import tomorimod.powers.GravityPower;

import java.util.Locale;

import static tomorimod.TomoriMod.makeID;

public class DivergeWorldPower extends BasePower {
    public static final String POWER_ID = makeID(DivergeWorldPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DivergeWorldPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    @Override
    public void onAttack(DamageInfo info, int damageAmount,AbstractCreature target) {
        if (info.type != DamageInfo.DamageType.THORNS) {
            flash();
            if(owner == AbstractDungeon.player){
                if (AbstractDungeon.player.hasPower(makeID("GravityPower"))) {
                    for (AbstractPower power : AbstractDungeon.player.powers) {
                        if (power.ID.equals(makeID("GravityPower"))) {
                            for(int i=0;i<amount;i++){
                                ((GravityPower) power).applyEffect();
                            }
                            break;
                        }
                    }
                }
            }else{
                if (owner.hasPower(makeID("GravityPower"))) {
                    if(owner.getPower(makeID("GravityPower")).amount!=0){
                        for(int i=0;i<amount;i++){
                            addToBot(new DamageAction(AbstractDungeon.player,
                                    new DamageInfo(this.owner, owner.getPower(makeID("GravityPower")).amount,
                                            DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                        }
                    }
                }
            }
        }
    }
}
