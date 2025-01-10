package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import tomorimod.cards.music.Bitianbanzou;
import tomorimod.character.Tomori;
import tomorimod.configs.TomoriConfig;
import tomorimod.rooms.EndingEventRoom;

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


}

