package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class SoyoFormPower extends BasePower {
    public static final String POWER_ID = makeID(SoyoFormPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private String color=null;

    public SoyoFormPower(AbstractCreature owner,int amount,String color) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.color=color;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(color.equals("red")){
            return damage*(2.0f+0.5f*amount);
        }
        return damage;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(color.equals("red")){
            return damage*2.0f;
        }
        return damage;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer){
        if(color.equals("green")){
            addToBot(new HealAction(owner,owner,20+5*amount));
            addToBot(new HealAction(AbstractDungeon.player,owner,20+5*amount));
        }
        if(color.equals("yellow")){
            addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner,4+amount),4+amount));
        }
    }

}
