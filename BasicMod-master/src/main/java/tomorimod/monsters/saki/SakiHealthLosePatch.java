package tomorimod.monsters.saki;

import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import tomorimod.character.Tomori;
import tomorimod.monsters.saki.SakiDamageInfo;
import tomorimod.powers.ImmunityPower;
import tomorimod.vfx.StarDustEffect;

import static tomorimod.TomoriMod.makeID;

// 你的自定义伤害信息类
// import your.package.SakiDamageInfo;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez = {DamageInfo.class}
)
public class SakiHealthLosePatch {

    public static class DamagePatch {
        @SpirePrefixPatch
        public static SpireReturn<?> prefix(AbstractPlayer __instance, DamageInfo damageInfo) {
            // 如果是我们定义的“真实伤害”，则跳过原逻辑，执行自定义方法
            if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT
                    && damageInfo instanceof SakiDamageInfo) {
                applyTrueDamage(__instance, damageInfo);
                return SpireReturn.Return(null);
            }
            // 否则继续原伤害逻辑
            return SpireReturn.Continue();
        }
    }

    /**
     * 仅扣血，不触发伤害量更改逻辑，但保留死亡、复活、Bloodied、StaggerAnimation、受伤统计等。
     */
    public static void applyTrueDamage(AbstractPlayer player, DamageInfo info) {
        int damageAmount = info.output;
        // 负伤害修正为0
        if (damageAmount < 0) {
            damageAmount = 0;
        }

        // 不做任何格挡、虚无(Intangible)或遗物/能力对伤害的修改
        // 不调用 powers / relics 对伤害的修正

        // 记录最后一次伤害（UI显示会用到）
        for(AbstractRelic r : player.relics) {
            damageAmount = r.onLoseHpLast(damageAmount);
        }
        player.lastDamageTaken = Math.min(damageAmount, player.currentHealth);

        // 真正扣血
        if (damageAmount > 0) {
            for(AbstractRelic r : player.relics) {
                r.onLoseHp(damageAmount);
            }

            for(AbstractPower p : player.powers) {
                p.wasHPLost(info, damageAmount);
            }

            for(AbstractRelic r : player.relics) {
                r.wasHPLost(damageAmount);
            }
            // 如果是 HP_LOSS 类型，也可记录到总计
            if (info.type == DamageInfo.DamageType.HP_LOSS) {
                GameActionManager.hpLossThisCombat += damageAmount;
            }
            // 记录统计
            GameActionManager.damageReceivedThisTurn += damageAmount;
            GameActionManager.damageReceivedThisCombat += damageAmount;

            // 扣血
            player.currentHealth -= damageAmount;

            // 播放受击动画（若伤害来源不是自己）
            if (info.owner != player) {
                player.useStaggerAnimation();
            }

            // 更新卡牌、统计次数
            if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
                if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
                    for(AbstractCard c : player.hand.group) {
                        c.tookDamage();
                    }

                    for(AbstractCard c : player.discardPile.group) {
                        c.tookDamage();
                    }

                    for(AbstractCard c : player.drawPile.group) {
                        c.tookDamage();
                    }
                }
                player.damagedThisCombat++;
            }

            // 播放受击视觉效果
            AbstractDungeon.effectList.add(new StrikeEffect(
                    player,
                    player.hb.cX,
                    player.hb.cY,
                    damageAmount));

            // 如果血量掉到 <0，就设为0
            if (player.currentHealth < 0) {
                player.currentHealth = 0;
            }
            // 如果血量 < 25%最大生命值，就闪红框
            else if (player.currentHealth < player.maxHealth / 4) {
                AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(
                        new Color(1.0F, 0.1F, 0.05F, 0.0F)));
            }

            // 更新血条 UI
            player.healthBarUpdatedEvent();

            // 血量小于等于一半 -> 进入 Bloodied 状态
            if (!player.isBloodied && (float) player.currentHealth <= (float) player.maxHealth / 2.0F) {
                player.isBloodied = true;
                // 触发遗物的 onBloodied()
                for (AbstractRelic r : player.relics) {
                    if (r != null) {
                        r.onBloodied();
                    }
                }
            }

            // 如果当前血量 < 1（即 0），则检查是否会被复活，否则执行死亡逻辑
            if (player.currentHealth < 1) {
                // 如果没有“Mark of the Bloom”，可以尝试复活逻辑
                if (!player.hasRelic("Mark of the Bloom")) {
                    // 先检查是否有仙女药水 FairyPotion
                    if (player.hasPotion("FairyPotion")) {
                        for (AbstractPotion p : player.potions) {
                            if (p.ID.equals("FairyPotion")) {
                                p.flash();
                                player.currentHealth = 0; // 先归零血量
                                p.use(player);            // 使用复活
                                AbstractDungeon.topPanel.destroyPotion(p.slot);
                                return;
                            }
                        }
                    }
                    // 没有仙女药水，就检查蜥蜴尾巴 Lizard Tail
                    else if (player.hasRelic("Lizard Tail")
                            && ((LizardTail) player.getRelic("Lizard Tail")).counter == -1) {
                        player.currentHealth = 0; // 先归零血量
                        player.getRelic("Lizard Tail").onTrigger(); // 触发蜥蜴尾巴复活
                        return;
                    }
                }

                // 如果前面都没能复活，玩家死亡
                player.isDead = true;
                AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
                player.currentHealth = 0;

                // 如果此时仍有格挡，则清空并播放破盾效果
//                if (player.currentBlock > 0) {
//                    player.loseBlock();
//                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(
//                            player.hb.cX - player.hb.width / 2.0F + AbstractCreature.BLOCK_ICON_X,
//                            player.hb.cY - player.hb.height / 2.0F + AbstractCreature.BLOCK_ICON_Y));
//                }
            }
        }
        else {
            // 伤害量 == 0 时，你可以决定要不要播放任何效果
            // 这里演示一下与原版类似的行为：播放一次 StrikeEffect( 0 ) 或 BlockedWordEffect 等
            AbstractDungeon.effectList.add(new StrikeEffect(
                    player,
                    player.hb.cX,
                    player.hb.cY,
                    0));
        }
    }

    public static void applyTrueDamageTomori(Tomori player, DamageInfo info) {
        int damageAmount = info.output;
        // 负伤害修正为0
        if (damageAmount < 0) {
            damageAmount = 0;
        }

        // 不做任何格挡、虚无(Intangible)或遗物/能力对伤害的修改
        // 不调用 powers / relics 对伤害的修正

        // 记录最后一次伤害（UI显示会用到）
        for(AbstractRelic r : player.relics) {
            damageAmount = r.onLoseHpLast(damageAmount);
        }
        player.lastDamageTaken = Math.min(damageAmount, player.currentHealth);

        // 真正扣血
        if (damageAmount > 0) {
            for(AbstractRelic r : player.relics) {
                r.onLoseHp(damageAmount);
            }

            for(AbstractPower p : player.powers) {
                p.wasHPLost(info, damageAmount);
            }

            for(AbstractRelic r : player.relics) {
                r.wasHPLost(damageAmount);
            }
            // 如果是 HP_LOSS 类型，也可记录到总计
            if (info.type == DamageInfo.DamageType.HP_LOSS) {
                GameActionManager.hpLossThisCombat += damageAmount;
            }
            // 记录统计
            GameActionManager.damageReceivedThisTurn += damageAmount;
            GameActionManager.damageReceivedThisCombat += damageAmount;

            // 扣血
            player.currentHealth -= damageAmount;

            // 播放受击动画（若伤害来源不是自己）
            if (info.owner != player) {
                player.useStaggerAnimation();
            }

            // 更新卡牌、统计次数
            if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
                if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
                    for(AbstractCard c : player.hand.group) {
                        c.tookDamage();
                    }

                    for(AbstractCard c : player.discardPile.group) {
                        c.tookDamage();
                    }

                    for(AbstractCard c : player.drawPile.group) {
                        c.tookDamage();
                    }
                }
                player.damagedThisCombat++;
            }

            // 播放受击视觉效果
            AbstractDungeon.effectList.add(new StrikeEffect(
                    player,
                    player.hb.cX,
                    player.hb.cY,
                    damageAmount));

            // 如果血量掉到 <0，就设为0
            if (player.currentHealth < 0) {
                player.currentHealth = 0;
            }
            // 如果血量 < 25%最大生命值，就闪红框
            else if (player.currentHealth < player.maxHealth / 4) {
                AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(
                        new Color(1.0F, 0.1F, 0.05F, 0.0F)));
            }

            // 更新血条 UI
            player.healthBarUpdatedEvent();

            // 血量小于等于一半 -> 进入 Bloodied 状态
            if (!player.isBloodied && (float) player.currentHealth <= (float) player.maxHealth / 2.0F) {
                player.isBloodied = true;
                // 触发遗物的 onBloodied()
                for (AbstractRelic r : player.relics) {
                    if (r != null) {
                        r.onBloodied();
                    }
                }
            }

            // 如果当前血量 < 1（即 0），则检查是否会被复活，否则执行死亡逻辑
            if (player.currentHealth < 1) {
                // 如果没有“Mark of the Bloom”，可以尝试复活逻辑
                if (!player.hasRelic("Mark of the Bloom")) {
                    // 先检查是否有仙女药水 FairyPotion
                    if (player.hasPotion("FairyPotion")) {
                        for (AbstractPotion p : player.potions) {
                            if (p.ID.equals("FairyPotion")) {
                                p.flash();
                                player.currentHealth = 0; // 先归零血量
                                p.use(player);            // 使用复活
                                AbstractDungeon.topPanel.destroyPotion(p.slot);
                                return;
                            }
                        }
                    }
                    // 没有仙女药水，就检查蜥蜴尾巴 Lizard Tail
                    else if (player.hasRelic("Lizard Tail")
                            && ((LizardTail) player.getRelic("Lizard Tail")).counter == -1) {
                        player.currentHealth = 0; // 先归零血量
                        player.getRelic("Lizard Tail").onTrigger(); // 触发蜥蜴尾巴复活
                        return;
                    } else if (player.hasPower(makeID("StarDustPower"))) {
                        player.doStarDustPowerLogic();
                        return;
                    } else if (player.hasPower(makeID("ImmunityPower"))) {
                        player.doImmunityPowerLogic();
                        return;
                    }
                }
                // 如果前面都没能复活，玩家死亡
                player.isDead = true;
                AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
                player.currentHealth = 0;

            }
        }
        else {
            // 伤害量 == 0 时，你可以决定要不要播放任何效果
            // 这里演示一下与原版类似的行为：播放一次 StrikeEffect( 0 ) 或 BlockedWordEffect 等
            AbstractDungeon.effectList.add(new StrikeEffect(
                    player,
                    player.hb.cX,
                    player.hb.cY,
                    0));
        }
    }
}
