package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import tomorimod.monsters.BaseMonster;

import java.lang.reflect.Field;
import java.util.Iterator;

import static tomorimod.TomoriMod.makeID;

public abstract class SpecialMonster extends BaseMonster {

    public AbstractCreature target;
    public boolean isMultiTarget=false;

    public SpecialMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

//    @SpireOverride
//    public void calculateDamage(int dmg) {
//
//        int tmp;
//        if(isMultiTarget|| isHardMode){
//            tmp=calculateDamageMulti(dmg); //指伤害不考虑不目标
//        }else{
//            tmp=calculateDamageSingle(dmg,this.target); //伤害考虑目标
//        }
//        setPrivateField(this, "intentDmg", tmp);
//    }

    public int calculateDamageSingle(int dmg,AbstractCreature target){
        //AbstractCreature target;
        if(target==null){
            target=AbstractDungeon.player;
        }

        float tmp = (float)dmg;
        if (Settings.isEndless && AbstractDungeon.player.hasBlight("DeadlyEnemies")) {
            float mod = AbstractDungeon.player.getBlight("DeadlyEnemies").effectFloat();
            tmp *= mod;
        }

        AbstractPower p;
        Iterator var6;
        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        if(target==AbstractDungeon.player){
            tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL);
        }

//        for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL)) {
//
//            p = (AbstractPower)var6.next();
//        }
        for (AbstractPower power : target.powers) {
            if(hasPower(makeID("MutsumiRealDamagePower"))&&power.ID.equals("IntangiblePlayer")){
                continue;
            }else{
                tmp = power.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL);
            }
        }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }
        return dmg;
    }

    public int calculateDamageMulti(int dmg){
        AbstractCreature target;
        float tmp = (float)dmg;

        AbstractPower p;
        Iterator var6;
        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }
        return dmg;
    }

    @Override
    public void damage(DamageInfo info) {
        if(info.owner==AbstractDungeon.player){
            super.damage(info);
        }else{
            //info.applyPowers(info.owner,this);

            if (info.output > 0 && this.hasPower("IntangiblePlayer")) {
                info.output = 1;
            }

            int damageAmount = info.output;
            if (!this.isDying && !this.isEscaping) {
                if (damageAmount < 0) {
                    damageAmount = 0;
                }

                boolean hadBlock = true;
                if (this.currentBlock == 0) {
                    hadBlock = false;
                }

                boolean weakenedToZero = damageAmount == 0;
                damageAmount = this.decrementBlock(info, damageAmount);
                Iterator var5;
                AbstractRelic r;

                AbstractPower p;
                if (info.owner != null) {
                    for(var5 = info.owner.powers.iterator(); var5.hasNext(); damageAmount = p.onAttackToChangeDamage(info, damageAmount)) {
                        p = (AbstractPower)var5.next();
                    }
                }

                for(var5 = this.powers.iterator(); var5.hasNext(); damageAmount = p.onAttackedToChangeDamage(info, damageAmount)) {
                    p = (AbstractPower)var5.next();
                }

                var5 = this.powers.iterator();

                while(var5.hasNext()) {
                    p = (AbstractPower)var5.next();
                    p.wasHPLost(info, damageAmount);
                }

                if (info.owner != null) {
                    var5 = info.owner.powers.iterator();

                    while(var5.hasNext()) {
                        p = (AbstractPower)var5.next();
                        p.onAttack(info, damageAmount, this);
                    }
                }

                for(var5 = this.powers.iterator(); var5.hasNext(); damageAmount = p.onAttacked(info, damageAmount)) {
                    p = (AbstractPower)var5.next();
                }

//                for(var5 = this.powers.iterator(); var5.hasNext(); damageAmount = (int)p.atDamageReceive(damageAmount,info.type)) {
//                    p = (AbstractPower)var5.next();
//                }

                this.lastDamageTaken = Math.min(damageAmount, this.currentHealth);
                boolean probablyInstantKill = this.currentHealth == 0;
                if (damageAmount > 0) {
                    if (info.owner != this) {
                        this.useStaggerAnimation();
                    }

                    if (damageAmount >= 99 && !CardCrawlGame.overkill) {
                        CardCrawlGame.overkill = true;
                    }

                    this.currentHealth -= damageAmount;
                    if (!probablyInstantKill) {
                        AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));
                    }

                    if (this.currentHealth < 0) {
                        this.currentHealth = 0;
                    }

                    this.healthBarUpdatedEvent();
                } else if (!probablyInstantKill) {
                    if (weakenedToZero && this.currentBlock == 0) {
                        if (hadBlock) {
                            AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, TEXT[30]));
                        } else {
                            AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
                        }
                    } else if (Settings.SHOW_DMG_BLOCK) {
                        AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, TEXT[30]));
                    }
                }

                if (this.currentHealth <= 0) {
                    this.die();
                    if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.cleanCardQueue();
                        AbstractDungeon.effectList.add(new DeckPoofEffect(64.0F * Settings.scale, 64.0F * Settings.scale, true));
                        AbstractDungeon.effectList.add(new DeckPoofEffect((float)Settings.WIDTH - 64.0F * Settings.scale, 64.0F * Settings.scale, false));
                        AbstractDungeon.overlayMenu.hideCombatPanels();
                    }

                    if (this.currentBlock > 0) {
                        this.loseBlock();
                        AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
                    }
                }

            }
        }
    }



    public static <T> T getPrivateField(Object instance, String fieldName, Class<T> fieldType) {
        try {
            Field field = AbstractMonster.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return fieldType.cast(field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void setPrivateField(Object instance, String fieldName, T value) {
        try {
            // 获取目标类的 Field 对象
            Field field = AbstractMonster.class.getDeclaredField(fieldName);

            // 取消 Java 语言访问检查以访问私有字段
            field.setAccessible(true);

            // 设置新值
            field.set(instance, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
