package tomorimod.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Iterator;

import static tomorimod.TomoriMod.makeID;

public class FriendlyMonsterPatch{
    @SpirePatch(
            clz = DamageAllEnemiesAction.class,
            method = "update"
    )
    public static class DamageAllEnemiesActionPatch {
        @SpireInsertPatch(
                rloc=32
        )
        public static SpireReturn<Void> insert(DamageAllEnemiesAction __instance) {
            if (__instance.isDone) {
                Iterator var4 = AbstractDungeon.player.powers.iterator();

                while(var4.hasNext()) {
                    AbstractPower p = (AbstractPower)var4.next();
                    p.onDamageAllEnemies(__instance.damage);
                }

                int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

                for(int i = 0; i < temp; ++i) {
                    if(!AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hasPower(makeID("FriendlyMonsterPower"))){
                        if (!((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDeadOrEscaped()) {
                            if (__instance.attackEffect == AbstractGameAction.AttackEffect.POISON) {
                                ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color.set(Color.CHARTREUSE);
                                ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                            } else if (__instance.attackEffect == AbstractGameAction.AttackEffect.FIRE) {
                                ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.color.set(Color.RED);
                                ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).tint.changeColor(Color.WHITE.cpy());
                            }

                            ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).damage(new DamageInfo(__instance.source, __instance.damage[i], __instance.damageType));
                        }
                    }
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
                }
            }
            return SpireReturn.Return();
        }




        @SpirePatch2(
                clz = MonsterGroup.class,
                method = "getRandomMonster",
                paramtypez={
                        AbstractMonster.class,
                        boolean.class,
                        Random.class
                }

        )
        public static class getRandomMonsterPatch{
            @SpirePrefixPatch
            public static SpireReturn<AbstractMonster> prefix(MonsterGroup __instance, AbstractMonster exception, boolean aliveOnly, Random rng){
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if (!monster.isDeadOrEscaped()&&monster.hasPower(makeID("FriendlyMonsterPower"))) {
                        exception=monster;
                    }
                }
                if (__instance.areMonstersBasicallyDead()) {
                    return SpireReturn.Return(null);
                } else {
                    ArrayList tmp;
                    Iterator var5;
                    AbstractMonster m;

                    if (exception == null) {
                        if (aliveOnly) {
                            tmp = new ArrayList();
                            var5 = __instance.monsters.iterator();

                            while(var5.hasNext()) {
                                m = (AbstractMonster)var5.next();
                                if (!m.halfDead && !m.isDying && !m.isEscaping) {
                                    tmp.add(m);
                                }
                            }

                            if (tmp.size() <= 0) {
                                return SpireReturn.Return(null);
                            } else {
                                return SpireReturn.Return((AbstractMonster)tmp.get(rng.random(0, tmp.size() - 1)));
                            }
                        } else {
                            return SpireReturn.Return((AbstractMonster)__instance.monsters.get(rng.random(0, __instance.monsters.size() - 1)));
                        }
                    } else if (__instance.monsters.size() == 1) {
                        return SpireReturn.Return((AbstractMonster)__instance.monsters.get(0));
                    } else if (aliveOnly) {
                        tmp = new ArrayList();
                        var5 = __instance.monsters.iterator();

                        while(var5.hasNext()) {
                            m = (AbstractMonster)var5.next();
                            if (!m.halfDead && !m.isDying && !m.isEscaping && !exception.equals(m)) {
                                tmp.add(m);
                            }
                        }

                        if (tmp.size() == 0) {
                            return SpireReturn.Return(null);
                        } else {
                            return SpireReturn.Return((AbstractMonster)tmp.get(rng.random(0, tmp.size() - 1)));
                        }
                    } else {
                        tmp = new ArrayList();
                        var5 = __instance.monsters.iterator();

                        while(var5.hasNext()) {
                            m = (AbstractMonster)var5.next();
                            if (!exception.equals(m)) {
                                tmp.add(m);
                            }
                        }
                        return SpireReturn.Return((AbstractMonster)tmp.get(rng.random(0, tmp.size() - 1)));
                    }
                }
            }
        }

    }
}
