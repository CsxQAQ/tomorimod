package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tomorimod.cards.music.Bitianbanzou;
import tomorimod.cards.permanentforms.PermanentFrom;
import tomorimod.savedata.customdata.CardsRemovedFromPoolSaveData;
import tomorimod.savedata.customdata.PermanentFormsSaveData;

import static tomorimod.TomoriMod.makeID;

public class RemoveFromCardPoolPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeCardPools"
    )
    public static class AbstractDungeonPostFixPatch {
        @SpirePostfixPatch
        public static void prefix(AbstractDungeon __instance, CardGroup ___rareCardPool) {
            for(String cardId: CardsRemovedFromPoolSaveData.getInstance().cardsRemoved){
                ___rareCardPool.removeCard(cardId);
            }

        }
    }
}

