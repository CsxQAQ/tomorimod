package tomorimod.monsters.mutsumioperator;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;
import tomorimod.savedata.customdata.CraftingRecipes;

import static tomorimod.TomoriMod.makeID;

public class SakiMachineDistancePower extends BasePower {
    public static final String POWER_ID = makeID(SakiMachineDistancePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SakiMachineDistancePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner,0);
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(owner instanceof SakiMachineMonster){
            int distance= ((SakiMachineMonster) owner).distance;
            if(distance>0){
                return damage/distance;
            }
        }
        return damage;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(owner instanceof SakiMachineMonster){
            int distance= ((SakiMachineMonster) owner).distance;
            if(distance>0){
                return damage/distance;
            }
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        if(owner instanceof SakiMachineMonster){
            int distance= ((SakiMachineMonster) owner).distance;
            if(distance>0){
                description=DESCRIPTIONS[0]+distance+DESCRIPTIONS[1]+100*(1.0F-1.0F/distance)+DESCRIPTIONS[2];
            }
        }
    }
}
