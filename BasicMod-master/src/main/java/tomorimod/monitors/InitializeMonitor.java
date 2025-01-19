package tomorimod.monitors;

import basemod.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.savedata.SaveDataInstanceFactory;
import tomorimod.savedata.customdata.*;
import tomorimod.util.CustomUtils;

//初始化地牢的时候，即重开，需要清空所有的自定义数据
public class InitializeMonitor extends BaseMonitor implements PostDungeonInitializeSubscriber, StartGameSubscriber {

    //游戏是先生成角色masterDeck再生成地牢，所以要给已生成卡牌补上材料
    public void allocateCardMaterial(){
        if(AbstractDungeon.player!=null){
            for(AbstractCard card:AbstractDungeon.player.masterDeck.group){
//                if(card instanceof BaseCard){
//                    ((BaseCard)card).initializeMaterialIcon();
//                }
                AbstractCardSetMaterialPatch.initializeMaterialIcon(card);
            }
        }
    }

    //游戏重开时补上音乐牌的稀有度
    public void allocateMusicRarity(){
        if(AbstractDungeon.player!=null){
            for(AbstractCard card:AbstractDungeon.player.masterDeck.group){
                if(card instanceof BaseMusicCard){
                    ((BaseMusicCard)card).setMusicRarity(BaseMusicCard.getMusicRarityByCost(card.cardID));
                    //((BaseMusicCard)card).setBanner();
                    //((BaseMusicCard)card).setDisplayRarity(card.rarity);
                }
            }
        }
    }

    //生成地牢时，清空原本的数据(保存游戏并退出但没有关闭游戏时数据留在内存内)，生成材料表，然后给已生成卡牌补上材料
    @Override
    public void receivePostDungeonInitialize() {
        if(AbstractDungeon.floorNum==0){
            SaveDataInstanceFactory.clearAll();
            CraftingRecipes.getInstance().generate();
            allocateCardMaterial();

            if(!CraftingRecipes.getInstance().recipeArrayList.isEmpty()){
                HistoryCraftRecords.getInstance().historyCraftRecords.add(
                        CraftingRecipes.getInstance().recipeArrayList.get(CraftingRecipes.getInstance().recipeArrayList.size()-1));
                HistoryCraftRecords.getInstance().historyCraftRecords.add(
                        CraftingRecipes.getInstance().recipeArrayList.get(CraftingRecipes.getInstance().recipeArrayList.size()-2));
            }
            //测试，笔记本自带两条记录

            AbstractDungeon.shopRelicPool.remove("PrismaticShard");
        }
    }

    //游戏重开时，材料表从存档加载，所以只要给已生成卡牌补上材料
    @Override
    public void receiveStartGame() {
        allocateCardMaterial();
        allocateMusicRarity();
        CustomUtils.initializeCards();
    }
}
