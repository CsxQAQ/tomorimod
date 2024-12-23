package tomorinmod.monitors;

import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.customcards.Revolution;
import tomorinmod.patches.AbstractCardSetMaterialPatch;
import tomorinmod.savedata.customdata.CraftingRecipes;
import tomorinmod.savedata.customdata.HistoryCraftRecords;
import tomorinmod.savedata.customdata.SaveMusicDiscoverd;

public class RevolutionMonitor extends BaseMonitor implements PostBattleSubscriber {

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if(Revolution.isRevolutionUsed){
            Revolution.isRevolutionUsed=false;

            CraftingRecipes.getInstance().cardMaterialHashMap.clear();
            CraftingRecipes.getInstance().initializeCardsMaterials();

            for(AbstractCard card:AbstractDungeon.player.masterDeck.group){
                AbstractCardSetMaterialPatch.initializeMaterialIcon(card);
            }
        }
    }
}
