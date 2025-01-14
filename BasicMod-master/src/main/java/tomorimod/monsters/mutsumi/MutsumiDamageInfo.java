package tomorimod.monsters.mutsumi;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.Iterator;

public class MutsumiDamageInfo extends DamageInfo {

    public MutsumiDamageInfo(AbstractCreature damageSource, int base, DamageType type) {
        super(damageSource, base, type);
    }

    public MutsumiDamageInfo(AbstractCreature owner, int base) {
        this(owner, base, DamageInfo.DamageType.NORMAL);
    }

    public void applyPowers(AbstractCreature owner, AbstractCreature target) {
        this.output = this.base;
        this.isModified = false;
        float tmp = (float)this.output;
        AbstractPower p;
        Iterator var6;
        if (!owner.isPlayer) {
            if (Settings.isEndless && AbstractDungeon.player.hasBlight("DeadlyEnemies")) {
                float mod = AbstractDungeon.player.getBlight("DeadlyEnemies").effectFloat();
                tmp *= mod;
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            var6 = owner.powers.iterator();

            while(var6.hasNext()) {
                p = (AbstractPower)var6.next();
                tmp = p.atDamageGive(tmp, this.type);
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            var6 = target.powers.iterator();

            while(var6.hasNext()) {
                p = (AbstractPower)var6.next();
                tmp = p.atDamageReceive(tmp, this.type);
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            /////

            if(target==AbstractDungeon.player){
                tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, this.type);
            }

            /////
            if (this.base != (int)tmp) {
                this.isModified = true;
            }

            var6 = owner.powers.iterator();

            while(var6.hasNext()) {
                p = (AbstractPower)var6.next();
                tmp = p.atDamageFinalGive(tmp, this.type);
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            var6 = target.powers.iterator();

            while(var6.hasNext()) {
                p = (AbstractPower)var6.next();

                /////

                if(MutsumiRealDamagePatch.isMutsumi()&&p.ID.equals("IntangiblePlayer")){
                    tmp=tmp;
                }else{
                    tmp = p.atDamageFinalReceive(tmp, this.type);
                }

                ////
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            this.output = MathUtils.floor(tmp);
            if (this.output < 0) {
                this.output = 0;
            }
        } else {
            var6 = owner.powers.iterator();

            while(var6.hasNext()) {
                p = (AbstractPower)var6.next();
                tmp = p.atDamageGive(tmp, this.type);
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            tmp = AbstractDungeon.player.stance.atDamageGive(tmp, this.type);

            if (this.base != (int)tmp) {
                this.isModified = true;
            }

            var6 = target.powers.iterator();

            while(var6.hasNext()) {
                p = (AbstractPower)var6.next();
                tmp = p.atDamageReceive(tmp, this.type);
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            var6 = owner.powers.iterator();

            while(var6.hasNext()) {
                p = (AbstractPower)var6.next();
                tmp = p.atDamageFinalGive(tmp, this.type);
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            var6 = target.powers.iterator();

            while(var6.hasNext()) {
                p = (AbstractPower)var6.next();
                tmp = p.atDamageFinalReceive(tmp, this.type);
                if (this.base != (int)tmp) {
                    this.isModified = true;
                }
            }

            this.output = MathUtils.floor(tmp);
            if (this.output < 0) {
                this.output = 0;
            }
        }

    }
}
