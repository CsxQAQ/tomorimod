package tomorimod.monsters.sakishadow;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CNCardTextColors;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
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
            method="damage"
    )
    public static class DamagePatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void insert(AbstractPlayer __instance){
            if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
                AbstractPower power= MonsterUtils.getPower(makeID("SakiShadowMonster"),makeID("SakiShadowRightPower"));
                if(power!=null&&power instanceof SakiShadowRightPower){
                    ((SakiShadowRightPower) power).afterDamage();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "currentHealth");
                int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
                return new int[]{lines[1]+1};
            }
        }
    }



}


