package tomorimod.monitors.card;

import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.cards.customcards.Revolution;
import tomorimod.monitors.BaseMonitor;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.savedata.customdata.CraftingRecipes;

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
