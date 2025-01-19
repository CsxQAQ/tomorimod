package tomorimod.monsters.uika;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.stats.BattleStats;
import tomorimod.cards.customcards.LightAndShadow;
import tomorimod.cards.customcards.NeedAnon;
import tomorimod.cards.uikacard.UikaLastOne;
import tomorimod.cards.uikacard.UikaLiveForever;
import tomorimod.monsters.WarningUi;
import tomorimod.util.CustomUtils;
import tomorimod.util.MonsterUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static tomorimod.TomoriMod.makeID;

public class UikaWarningUi extends WarningUi {
    public UikaMonster uikaMonster;
    public int gravityDamageForShow;

    public UikaWarningUi(UikaMonster monster) {
        super(monster);
        uikaMonster=monster;
    }

    @Override
    public void setDamageForShow(){
        if(!damageFrozen){
            damageForShow =calculate().get(0);
            gravityDamageForShow =calculate().get(1);
        }
    }

    @Override
    public void renderTip(){
        if (this.attackIntentHb.hovered||this.damageNumberHb.hovered) {
            // 这类方法可以渲染一个简单的提示
            // 参数：Tip的左下角X, Tip的左下角Y, 标题, 内容
            TipHelper.renderGenericTip(
                    InputHelper.mX + 50.0F * Settings.scale,  // Tip往右下方一点
                    InputHelper.mY - 50.0F * Settings.scale,
                    "预警",
                    "敌人将对你造成 #b" + damageForShow+ " 点伤害。 NL #b"+(damageForShow-gravityDamageForShow) +
                            " 点来自攻击。 NL #b"+gravityDamageForShow+" 点来自 #y重力 。"
            );
        }
    }



    public ArrayList<Integer> calculate() {
        // 1. 读取怪物信息
        int monsterDamage = getPublicField(monster, "intentDmg", Integer.class);
        int attackCount = getPublicField(monster, "intentMultiAmt", Integer.class);
        if (attackCount == -1) {
            attackCount = 1;
        }

        // 2. 初始化统计对象
        BattleStats stats = new BattleStats();
        stats.gravityAmount     = MonsterUtils.getPowerNum(monster, makeID("GravityPower"));
        stats.divergeWorldAmount= MonsterUtils.getPowerNum(monster, makeID("DivergeWorldPower"));
        stats.shineAmount       = MonsterUtils.getPowerNum(monster, makeID("ShinePower"));

        // 3. 判断玩家是否有 IntangiblePlayer
        boolean intangible = AbstractDungeon.player.hasPower("IntangiblePlayer");

        // 4. 分别处理两张 cardForShow (如果存在)
        applyCardEffect(uikaMonster.cardForShow1, monsterDamage, intangible, stats);
        applyCardEffect(uikaMonster.cardForShow2, monsterDamage, intangible, stats);

        // 5. 最后叠加伤害
        if (intangible) {
            // 虚无状态下，伤害和重力伤都只加 1
            stats.damageNum        += 1;
            stats.gravityDamageNum += 1;
        } else {
            // 否则加上 gravityAmount
            stats.damageNum        += stats.gravityAmount;
            stats.gravityDamageNum += stats.gravityAmount;
        }

        // 6. 返回结果
        return new ArrayList<>(Arrays.asList(stats.damageNum, stats.gravityDamageNum));
    }

    /**
     * 将对单张卡的处理封装到此方法，避免重复写分支。
     * 可以根据需要继续做合并，比如多个分支逻辑完全相同，可以用 case 穿透来简化。
     */
    private void applyCardEffect(AbstractCard card, int monsterDamage, boolean intangible, BattleStats stats) {
        if (card == null) return;

        String name   = CustomUtils.idToName(card.cardID);
        int magic   = card.magicNumber;

        switch (name) {
            case "UikaMygoTogether":
                stats.gravityAmount += magic;
                stats.shineAmount += magic;
                break;

            case "UikaLiveForever":
                // 两者逻辑一致，可以合并
                stats.gravityAmount += magic;
                break;

            case "UikaLightAndShadow":
                if (stats.gravityAmount >= stats.shineAmount) {
                    stats.gravityAmount += magic;
                } else {
                    stats.shineAmount += magic;
                }
                break;

            case "UikaNeedAnon":
                stats.gravityAmount += stats.gravityAmount*magic;
                break;

            case "UikaDivergeWorld":
                stats.divergeWorldAmount++;
                break;

            case "UikaLastGentle":
                int tmp = stats.gravityAmount;
                stats.gravityAmount = stats.shineAmount;
                stats.shineAmount = tmp;
                break;

            case "UikaStrike":
            case "UikaTwoMoon":
                // 先加上怪物的基础伤害
                stats.damageNum += monsterDamage;
                // 额外部分要根据是否虚无状态计算
                if (intangible) {
                    stats.damageNum        += stats.divergeWorldAmount;
                    stats.gravityDamageNum += stats.divergeWorldAmount;
                } else {
                    int extra = stats.divergeWorldAmount * stats.gravityAmount;
                    stats.damageNum        += extra;
                    stats.gravityDamageNum += extra;
                }
                break;

            case "UikaLastOne":
                stats.shineAmount += magic;
                break;

            case "UikaPoemInsteadOfSong":
                stats.shineAmount += uikaMonster.getDebuffNum();
                break;

            default:
                // 其他卡ID无特殊逻辑
                break;
        }
    }

    private static class BattleStats {
        int gravityAmount;
        int divergeWorldAmount;
        int shineAmount;
        int damageNum;
        int gravityDamageNum;
    }


//    public ArrayList<Integer> calculate(){
//        int damageNum=0;
//        int gravityDamageNum=0;
//        int monsterDamage = getPublicField(monster, "intentDmg", Integer.class);
//        int attackCount = getPublicField(monster, "intentMultiAmt", Integer.class);
//        if(attackCount==-1){
//            attackCount=1;
//        }
//        int gravityAmount= MonsterUtils.getPowerNum(monster, makeID("GravityPower"));
//        int divergeWorldAmount=MonsterUtils.getPowerNum(monster, makeID("divergeWorldPower"));
//        int shineAmount=MonsterUtils.getPowerNum(monster, makeID("ShinePower"));
//
//        if(uikaMonster.cardForShow1!=null){
//            if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaMygoTogether"))){
//                gravityAmount++;
//            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaLiveForever"))){
//                gravityAmount+= UikaLiveForever.MAGIC;
//            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaLightAndShadow"))){
//                if(gravityAmount>=shineAmount){
//                    gravityAmount+= LightAndShadow.MAGIC;
//                }else{
//                    shineAmount+=LightAndShadow.MAGIC;
//                }
//            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaNeedAnon"))){
//                gravityAmount+=gravityAmount* NeedAnon.MAGIC;
//            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaDivergeWorld"))){
//                divergeWorldAmount++;
//            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaLastGentle"))){
//                int tmp=gravityAmount;
//                gravityAmount=shineAmount;
//                shineAmount=tmp;
//            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaStrike"))||uikaMonster.cardForShow1.cardID.equals(makeID("UikaTwoMoon"))){
//                damageNum+=monsterDamage;
//                if(AbstractDungeon.player.hasPower("IntangiblePlayer")){
//                    damageNum+=divergeWorldAmount;
//                    gravityDamageNum+=divergeWorldAmount;
//                }else{
//                    damageNum+=divergeWorldAmount*gravityAmount;
//                    gravityDamageNum+=divergeWorldAmount*gravityAmount;
//                }
//            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaLastOne"))){
//                shineAmount+= UikaLastOne.MAGIC;
//            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaPoemInsteadOfSong"))){
//                shineAmount+=uikaMonster.getDebuffNum();
//            }
//        }
//
//        if(uikaMonster.cardForShow2!=null){
//            if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaMygoTogether"))){
//                gravityAmount++;
//            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaLiveForever"))){
//                gravityAmount+=UikaLiveForever.MAGIC;
//            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaLightAndShadow"))){
//                if(gravityAmount>=shineAmount){
//                    gravityAmount+=LightAndShadow.MAGIC;
//                }else{
//                    shineAmount+=LightAndShadow.MAGIC;
//                }
//            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaNeedAnon"))){
//                gravityAmount+=gravityAmount*NeedAnon.MAGIC;
//            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaDivergeWorld"))){
//                divergeWorldAmount++;
//            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaLastGentle"))){
//                int tmp=gravityAmount;
//                gravityAmount=shineAmount;
//                shineAmount=tmp;
//            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaStrike"))||uikaMonster.cardForShow2.cardID.equals(makeID("UikaTwoMoon"))){
//                damageNum+=monsterDamage;
//                if(AbstractDungeon.player.hasPower("IntangiblePlayer")){
//                    damageNum+=divergeWorldAmount;
//                    gravityDamageNum+=divergeWorldAmount;
//                }else{
//                    damageNum+=divergeWorldAmount*gravityAmount;
//                    gravityDamageNum+=divergeWorldAmount*gravityAmount;
//                }
//            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaLastOne"))){
//                shineAmount+=UikaLastOne.MAGIC;
//            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaPoemInsteadOfSong"))){
//                shineAmount+=uikaMonster.getDebuffNum();
//            }
//        }
//
//        if(AbstractDungeon.player.hasPower("IntangiblePlayer")){
//            damageNum+=1;
//            gravityDamageNum+=1;
//        }else{
//            damageNum+=gravityAmount;
//            gravityDamageNum+=gravityAmount;
//        }
//
//        return new ArrayList<>(Arrays.asList(damageNum,gravityDamageNum));
//    }
}
