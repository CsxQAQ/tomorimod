package tomorinmod.monitor;

import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.rare.Revolution;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.savedata.HistoryCraftRecords;
import tomorinmod.savedata.SaveMusicDiscoverd;
import tomorinmod.tags.CustomTags;

import java.util.Iterator;

public class RevolutionMonitor implements PostBattleSubscriber {

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if(Revolution.isRevolutionUsed){
            Revolution.isRevolutionUsed=false;
            HistoryCraftRecords.getInstance().clear();
            CraftingRecipes.getInstance().clear();
            CraftingRecipes.getInstance().generate();
            SaveMusicDiscoverd.getInstance().clear();
            for(AbstractCard abstractCard:AbstractDungeon.player.masterDeck.group){
                if(abstractCard instanceof BaseCard){
                    BaseCard baseCard=(BaseCard) abstractCard;
                    baseCard.initializeMaterial();
                }
            }
        }

    }


}
