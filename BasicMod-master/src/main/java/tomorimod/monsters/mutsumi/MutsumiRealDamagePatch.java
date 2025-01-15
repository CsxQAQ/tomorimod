package tomorimod.monsters.mutsumi;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import tomorimod.cards.notshow.mutsumi.Cucumber;
import tomorimod.util.MonsterUtils;

import java.util.ArrayList;
import java.util.Locale;

import static tomorimod.TomoriMod.makeID;

public class MutsumiRealDamagePatch {


//    @SpirePatch(
//            clz = AbstractPlayer.class,
//            method = "damage",
//            paramtypez = {
//                    DamageInfo.class
//            }
//    )
//    public static class TrueDamageInstrumentPatch {
//
//        @SpireInstrumentPatch
//        public static ExprEditor Instrument() {
//            return new ExprEditor() {
//                @Override
//                public void edit(MethodCall m) throws CannotCompileException {
//                    // 拦截对 hasPower 的调用
//                    if (m.getMethodName().equals("hasPower")) {
//
//                        m.replace(
//                                "if("+
//                                        MutsumiRealDamagePatch.class.getName()+
//                                        ".isMutsumi()) {" +
//                                        "    $_ = false;" +
//                                        "} else {" +
//                                        "    $_ = $proceed($$);" +
//                                        "}"
//                        );
//                    }
//                    // 拦截 decrementBlock 调用
//                    else if (m.getMethodName().equals("decrementBlock")) {
//
//                        m.replace(
//                                "if("+
//                                        MutsumiRealDamagePatch.class.getName()+
//                                        ".isMutsumi()) {" +
//                                        "    $_ = $1.output;" +
//                                        "} else {" +
//                                        "    $_ = $proceed($$);" +
//                                        "}"
//                        );
//                    }
//                }
//            };
//        }
//    }

    //两个地方判断，一个是DamageInfo的applyPowers，一个是玩家的damage方法
//    @SpirePatch(
//            clz = DamageInfo.class,
//            method = "applyPowers" ,
//            paramtypez = {
//                    AbstractCreature.class,
//                    AbstractCreature.class
//            }
//    )
//    public static class DamageInfoApplyPowersPatch {
//
//        @SpireInstrumentPatch
//        public static ExprEditor Instrument() {
//            return new ExprEditor() {
//                @Override
//                public void edit(MethodCall m) throws javassist.CannotCompileException {
//                    // 找到调用 p.atDamageFinalReceive(tmp, this.type) 的调用点
//                    if (m.getMethodName().equals("atDamageFinalReceive")) {
//                        // 修改此调用点逻辑，使用条件判断替换原来的调用
//                        m.replace("{ " +
//                                // 首先需要获得当前对象p与原始参数
//                                "if( " + MutsumiRealDamagePatch.class.getName() + ".isMutsumi() && $0.ID.equals(\"IntangiblePlayer\") ) { " +
//                                "    $_ = $1; " +  // 不修改 tmp，直接返回原来的参数
//                                "} else { " +
//                                "    $_ = $proceed($$); " +
//                                "}" +
//                                "}");
//                    }
//                }
//            };
//        }
//    }
    public static boolean isMutsumi(){
        if(AbstractDungeon.getCurrRoom().phase== AbstractRoom.RoomPhase.COMBAT){
            if(MonsterUtils.getPower(makeID("MutsumiMonster"),makeID("MutsumiRealDamagePower"))!=null){
                return true;
            }
        }
        return false;
    }
}
