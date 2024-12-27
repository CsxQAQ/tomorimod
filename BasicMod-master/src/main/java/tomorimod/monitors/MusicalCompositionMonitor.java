package tomorimod.monitors;

import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorimod.cards.basic.MusicComposition;
import tomorimod.cards.music.*;
import tomorimod.cards.special.FailComposition;
import tomorimod.effects.MaterialUiDelayClearAction;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.powers.MusicCompositionPower;
import tomorimod.rewards.MusicReward;
import tomorimod.savedata.customdata.CraftingRecipes;
import tomorimod.savedata.customdata.HistoryCraftRecords;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;
import tomorimod.screens.MaterialScreenProcessor;
import tomorimod.ui.MaterialUi;
import tomorimod.util.CustomUtils;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class MusicalCompositionMonitor extends BaseMonitor implements OnCardUseSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, PostUpdateSubscriber {

    public static final ArrayList<AbstractCard> cardsUsed = new ArrayList<>(3);

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(MusicComposition.isMusicCompositionUsed){

            if(!AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(abstractCard).isEmpty()){
                MaterialUi.getInstance().setMaterial(
                    AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(abstractCard),
                        AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(abstractCard));
                cardsUsed.add((abstractCard));
            }
            if(cardsUsed.size()==3){
                String music = matchRecipe();
                getMusic(music); //先getMusic是因为要先判断是否已创作过music来决定reward
                addHistoryRecipes(music);
                MusicComposition.isMusicCompositionUsed=false;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, MusicCompositionPower.POWER_ID));
                AbstractDungeon.actionManager.addToBottom(new MaterialUiDelayClearAction());
                cardsUsed.clear();
            }
            //}
        }
    }

    public void getMusic(String music){
        BaseMusicCard.MusicRarity musicRarity= BaseMusicCard.getMusicRarityByCost(music);
        BaseMusicCard card = null;

        if(music.equals("fail")){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FailComposition(), 1));
            return;
        }

//        ArrayList<BaseMusicCard> musicCardsGroup=CustomUtils.musicCardGroup;
//        for(BaseMusicCard musicCard:musicCardsGroup){
//            if(musicCard.cardID.equals(makeID(music))){
//                card=musicCard.makeStatEquivalentCopy();
//                break;
//            }
//        }
        card=CustomUtils.musicCardGroup.get(makeID(music)).makeStatEquivalentCopy();

        if (card != null) {
            card.setMusicRarity(musicRarity);
            if(!SaveMusicDiscoverd.getInstance().musicDiscovered.contains(music)){
                RewardItem musicReward = new MusicReward(card.cardID);
                AbstractDungeon.getCurrRoom().rewards.add(musicReward);
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1)); //这方法调用makeequalcopy所以会new新卡
        }
    }

    public void addHistoryRecipes(String music){
        ArrayList<String> records = new ArrayList<>(4);
        for (AbstractCard card : cardsUsed) {
            String material=CraftingRecipes.getInstance().cardMaterialHashMap.get(card.cardID);
            int level= AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card);
            records.add(material+level);
            //records.add(CraftingRecipes.getInstance().cardMaterialHashMap.get(card.cardID));
        }
        records.add(music);
        HistoryCraftRecords.getInstance().historyCraftRecords.add(records);

        if (!"fail".equals(music)) {
            SaveMusicDiscoverd.getInstance().musicAdd(music);
        }
    }


    private String matchRecipe() {
        for (CraftingRecipes.Recipe recipe : CraftingRecipes.getInstance().recipeArrayList) {
            if (isRecipeMatched(recipe)) {
                return recipe.music;
            }
        }
        return "fail";
    }

    private boolean isRecipeMatched(CraftingRecipes.Recipe recipe) {
        for (int i = 0; i < 3; i++) {
            if (!cardMatch(cardsUsed.get(i), recipe.needs.get(i), recipe.levels.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean cardMatch(AbstractCard card, String recipe, int rarity) {
        return AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card).equals(recipe)&&
                AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card)>=rarity;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        ScreenPostProcessorManager.removePostProcessor(MaterialScreenProcessor.getInstance());
    }

    @Override
    public void receivePostUpdate() {
        if(CardCrawlGame.mode!= CardCrawlGame.GameMode.GAMEPLAY){
            ScreenPostProcessorManager.removePostProcessor(MaterialScreenProcessor.getInstance());
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        //ScreenPostProcessorManager.addPostProcessor(MaterialScreenProcessor.getInstance());
        cardsUsed.clear();
        MaterialUi.getInstance().clear();
        MusicComposition.isMusicCompositionUsed=false;
    }
}

