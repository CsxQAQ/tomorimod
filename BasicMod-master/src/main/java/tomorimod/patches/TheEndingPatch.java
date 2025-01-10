package tomorimod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.cards.music.Bitianbanzou;
import tomorimod.character.Tomori;
import tomorimod.configs.TomoriConfig;
import tomorimod.rooms.EndingEventRoom;
import tomorimod.rooms.TomoriTrueVictoryRoom;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class TheEndingPatch {
    @SpirePatch(
            clz = ProceedButton.class,
            method = "goToVictoryRoomOrTheDoor"
    )
    public static class ProceedButtonPatch {
        @SpirePrefixPatch
        public static SpireReturn prefix(ProceedButton __instance){
            if(AbstractDungeon.player instanceof Tomori || TomoriConfig.config.getBool("onlyModBoss-enabled")){
                CardCrawlGame.music.fadeOutBGM();
                CardCrawlGame.music.fadeOutTempBGM();
                MapRoomNode node = new MapRoomNode(-1, 15);
                node.room = new EndingEventRoom();
                AbstractDungeon.nextRoom = node;
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.nextRoomTransitionStart();
                __instance.hide();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }


    @SpirePatch(
            clz = Cutscene.class,
            method = "update"
    )
    public static class CutscenePatch {
        @SpirePrefixPatch
        public static SpireReturn prefix(Cutscene __instance,Color ___bgColor,ArrayList<CutscenePanel> ___panels){
            ___bgColor.a -= Gdx.graphics.getDeltaTime();

//            for (CutscenePanel p : ___panels) {
//                if (!p.finished) {
//                    return;
//                }
//            }

            //dispose();
            ___bgColor.a = 0.0F;
            GameCursor.hidden = false;
            AbstractDungeon.victoryScreen = new VictoryScreen(null);
            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Cutscene.class, "isDone");

                int[] lines = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
                return new int[]{lines[0]};
            }
        }

    }
//    @SpirePatch(
//            clz = ProceedButton.class,
//            method = "goToTrueVictoryRoom"
//    )
//    public static class ProceedButtonTrueVictoryPatch {
//        @SpirePrefixPatch
//        public static SpireReturn<Void> prefix(ProceedButton __instance) {
//            if (AbstractDungeon.player instanceof Tomori || TomoriConfig.config.getBool("onlyModBoss-enabled")) {
//                CardCrawlGame.music.fadeOutBGM();
//                MapRoomNode node = new MapRoomNode(3, 4);
//                node.room = new TomoriTrueVictoryRoom();
//                AbstractDungeon.nextRoom = node;
//                AbstractDungeon.closeCurrentScreen();
//                AbstractDungeon.nextRoomTransitionStart();
//                __instance.hide();
//
//                return SpireReturn.Return(null);
//            }
//            return SpireReturn.Continue();
//        }
//
//    }


}

