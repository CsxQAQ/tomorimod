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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.credits.CreditsScreen;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.cards.music.Bitianbanzou;
import tomorimod.character.Tomori;
import tomorimod.configs.TomoriConfig;
import tomorimod.rooms.EndingEventRoom;
import tomorimod.rooms.TomoriTrueVictoryRoom;
import tomorimod.util.PlayerUtils;

import java.util.ArrayList;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class TheEndingPatch {
    @SpirePatch(
            clz = ProceedButton.class,
            method = "goToVictoryRoomOrTheDoor"
    )
    public static class ProceedButtonPatch {
        @SpirePrefixPatch
        public static SpireReturn prefix(ProceedButton __instance){
            if(PlayerUtils.isTomori()){
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
            clz = TrueVictoryRoom.class,
            method = "onPlayerEntry"
    )
    public static class TrueVictoryRoomPatch {
        @SpirePostfixPatch
        public static void postfix(TrueVictoryRoom __instance) {
            if (PlayerUtils.isTomori()) {
                CardCrawlGame.music.silenceBGMInstantly();
                CardCrawlGame.music.silenceTempBgmInstantly();
                CardCrawlGame.music.playTempBgmInstantly(MusicPatch.MusicHelper.GEORGETTE.name(), false);
            }
        }
        //new CreditsScreen
    }

    @SpirePatch(
            clz = NeowNarrationScreen.class,
            method = "update"
    )
    public static class NeowNarrationScreenPatch {
        @SpirePrefixPatch
        public static void prefix(NeowNarrationScreen __instance,float ___fadeOutTimer) {
            if (PlayerUtils.isTomori()) {
                ___fadeOutTimer=0f;
                GameCursor.hidden = false;
                CardCrawlGame.mainMenuScreen.lighten();
                CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
                CardCrawlGame.music.changeBGM("MENU");

            }
        }
    }

    @SpirePatch(
            clz = Cutscene.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractPlayer.PlayerClass.class
            }
    )
    public static class CutscenePanelsPatch {
        @SpirePostfixPatch
        public static void postfix(Cutscene __instance,AbstractPlayer.PlayerClass chosenClass,ArrayList<CutscenePanel> ___panels) {
            if (PlayerUtils.isTomori()) {
                ___panels.clear();
                ___panels.add(new CutscenePanel(imagePath("character/ed/page1.png")));
                ___panels.add(new CutscenePanel(imagePath("character/ed/page2.png")));
                ___panels.add(new CutscenePanel(imagePath("character/ed/page3.png")));
            }
        }
    }

    @SpirePatch(
            clz = CreditsScreen.class,
            method = "open",
            paramtypez = {
                    boolean.class
            }
    )
    public static class CreditsScreenPatch {
        @SpirePrefixPatch
        public static void prefix(CreditsScreen __instance,@ByRef boolean playCreditsBgm[]) {
            if (PlayerUtils.isTomori()) {
                CardCrawlGame.music.silenceBGMInstantly();
                CardCrawlGame.music.silenceTempBgmInstantly();
            }
        }
    }

}

