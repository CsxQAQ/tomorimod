package tomorimod.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.Iterator;

import static tomorimod.TomoriMod.makeID;

@SpirePatch(
        clz = DamageAllEnemiesAction.class,
        method = "update"
)
public class FriendlyMonsterPatch {
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
}