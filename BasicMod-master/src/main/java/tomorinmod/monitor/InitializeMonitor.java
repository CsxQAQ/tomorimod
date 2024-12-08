package tomorinmod.monitor;

import basemod.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.BaseCard;
import tomorinmod.savedata.*;

//初始化地牢的时候，即重开，需要清空所有的自定义数据
public class InitializeMonitor implements PostDungeonInitializeSubscriber, StartGameSubscriber, PostDeathSubscriber {

    public static boolean isInitialized=false;

    //大部分数据都应该在地牢初始化时清空并重新生成
    //除了isInitialized，设定在死亡时赋值
    //规范：每个要保存的数据实现clear和generate两个方法

    public void clear(){
        SaveForm.getInstance().clear();
        SavePermanentForm.getInstance().clear();
        SaveGifts.getInstance().clear();
        HistoryCraftRecords.getInstance().clear();
        CraftingRecipes.getInstance().clear();
    }

    public void initializeMaterials(){
        CraftingRecipes.getInstance().generate();
        isInitialized=true;
        for(BaseCard card:BaseCard.allInstances){
            card.initializeMaterial();
        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        if(AbstractDungeon.floorNum==0){
            clear();
            initializeMaterials();
        }
    }

    @Override
    public void receiveStartGame() {
        if(!isInitialized){
            return;
        }
        for(BaseCard card:BaseCard.allInstances){
            card.initializeMaterial();
        }
    }

    @Override
    public void receivePostDeath() {
        isInitialized=false;
    }
}
