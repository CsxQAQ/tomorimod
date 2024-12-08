package tomorinmod.monitor;

import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.BaseCard;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.savedata.SaveForm;
import tomorinmod.savedata.SaveGifts;
import tomorinmod.savedata.SavePermanentForm;

//初始化地牢的时候，即重开，需要清空所有的自定义数据
public class InitializeMonitor implements PostDungeonInitializeSubscriber {

    public static boolean isInitialized=false;

    public void initializeForm(){
        SaveForm.getInstance().clearForm();
        SavePermanentForm.getInstance().clearForm();
        SaveGifts.getInstance().initialize();
    }

    public void initializeMaterials(){
//        if (AbstractDungeon.floorNum == 0) {
//            isInitialized=true;
//            CraftingRecipes.getInstance().generate();
//            for(BaseCard card:BaseCard.allInstances){
//                card.initializeMaterial();
//            }
//        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        initializeForm();
        initializeMaterials();
    }



}
