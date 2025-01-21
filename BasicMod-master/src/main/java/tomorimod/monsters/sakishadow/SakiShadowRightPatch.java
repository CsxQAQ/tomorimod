package tomorimod.monsters.sakishadow;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CNCardTextColors;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.util.MonsterUtils;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class SakiShadowRightPatch {
    @SpirePatch(
            clz= AbstractPlayer.class,
            method="damage",
            paramtypez = {
                    DamageInfo.class
            }
    )
    public static class DamagePatch{
        @SpirePostfixPatch
        public static void postfix(AbstractPlayer __instance,DamageInfo damageInfo){
            applyAfterDamage(damageInfo);
        }

    }

    public static void applyAfterDamage(DamageInfo damageInfo){
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            if(damageInfo.owner instanceof SakiShadowMonster){
                AbstractPower power= MonsterUtils.getPower(makeID("SakiShadowMonster"),makeID("SakiShadowRightPower"));
                if(power instanceof SakiShadowRightPower){
                    ((SakiShadowRightPower) power).afterDamage();
                }
            }
        }
    }



}


