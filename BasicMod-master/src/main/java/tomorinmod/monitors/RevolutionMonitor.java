package tomorinmod.monitors;

import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.monment.Revolution;
import tomorinmod.patches.AbstractCardInsert3Patch;
import tomorinmod.savedata.customdata.CraftingRecipes;
import tomorinmod.savedata.customdata.HistoryCraftRecords;
import tomorinmod.savedata.customdata.SaveMusicDiscoverd;

public class RevolutionMonitor extends BaseMonitor implements PostBattleSubscriber {

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if(Revolution.isRevolutionUsed){
            Revolution.isRevolutionUsed=false;
            HistoryCraftRecords.getInstance().clear();
            CraftingRecipes.getInstance().clear();
            CraftingRecipes.getInstance().generate();
            SaveMusicDiscoverd.getInstance().clear();
            for(AbstractCard card:AbstractDungeon.player.masterDeck.group){
//                if(abstractCard instanceof BaseCard){
//                    BaseCard baseCard=(BaseCard) abstractCard;
//                    baseCard.initializeMaterialIcon();
//                }
                AbstractCardInsert3Patch.initializeMaterialIcon(card);
            }

        }
    }
}
