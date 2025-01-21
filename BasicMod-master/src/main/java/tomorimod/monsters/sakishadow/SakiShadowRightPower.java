package tomorimod.monsters.sakishadow;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import tomorimod.character.Tomori;
import tomorimod.powers.BasePower;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.ToDoubleBiFunction;

import static tomorimod.TomoriMod.makeID;

public class SakiShadowRightPower extends BasePower {
    public static final String POWER_ID = makeID(SakiShadowRightPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SakiShadowRightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void afterDamage() {
        AbstractPlayer p=AbstractDungeon.player;
        p.maxHealth-=this.amount;

        if(p.maxHealth<=0){
            if(p instanceof Tomori){
                if(p.hasPower(makeID("StarDustPower"))){
                    p.maxHealth=1;
                    p.currentHealth=0;
                    ((Tomori) p).doStarDustPowerLogic();
                    return;
                }else if(p.hasPower(makeID("ImmunityPower"))){
                    p.maxHealth=1;
                    p.currentHealth=0;
                    ((Tomori) p).doImmunityPowerLogic();
                    return;
                }
            }
        }
        p.currentHealth=Math.min(p.currentHealth,p.maxHealth);
        if(p.currentHealth<=0){
            p.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
            p.currentHealth = 0;
            if (p.currentBlock > 0) {
                p.loseBlock();
            }
        }
    }

}
