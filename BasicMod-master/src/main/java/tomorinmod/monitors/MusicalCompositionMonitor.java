package tomorinmod.monitors;

import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.basic.MusicalComposition;
import tomorinmod.cards.music.*;
import tomorinmod.powers.Shine;
import tomorinmod.rewards.MusicReward;
import tomorinmod.savedata.customdata.CraftingRecipes;
import tomorinmod.savedata.customdata.HistoryCraftRecords;
import tomorinmod.savedata.customdata.SaveMusicDiscoverd;
import tomorinmod.screens.MaterialScreenProcessor;

import java.util.ArrayList;

public class MusicalCompositionMonitor extends BaseMonitor implements OnCardUseSubscriber, OnStartBattleSubscriber, PostBattleSubscriber {

    public static final ArrayList<String> cardsUsed = new ArrayList<>(3);
    //private boolean added = false;

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(MusicalComposition.isMusicCompositionUsed){
            if(abstractCard instanceof BaseCard){
                BaseCard baseCard=(BaseCard) abstractCard;
                if(!baseCard.material.isEmpty()){ //string=""时isEmpty为true
                    MaterialScreenProcessor.drawImage(baseCard.material);
                    cardsUsed.add(abstractCard.cardID);
                }

                if(cardsUsed.size()==3){
                    String music = matchRecipe();
                    addHistoryRecipes(music);
                    getMusic(music);
                    MusicalComposition.isMusicCompositionUsed=false;
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, Shine.POWER_ID));
                    MaterialScreenProcessor.clear();
                }
            }
        }
    }

    public void addHistoryRecipes(String music){

        ArrayList<String> records = new ArrayList<>(4);
        for (String card : cardsUsed) {
            AbstractCard cardObject = CardLibrary.getCard(card);
            if(cardObject instanceof BaseCard){
                BaseCard baseCard=(BaseCard)cardObject;
                records.add(CraftingRecipes.getInstance().cardMaterialHashMap.get(baseCard.cardID));
            }
        }
        records.add(music);
        HistoryCraftRecords.getInstance().historyCraftRecords.add(records);

        if (!"fail".equals(music)) {
            SaveMusicDiscoverd.getInstance().musicDiscovered.add(music);
        }
    }

    public void getMusic(String music){

        BaseMusicCard.MusicRarity musicRarity= BaseMusicCard.getMusicRarityByCost(music);
        BaseMusicCard card;

        switch (music) {
            case "chunriying":
                card = new Chunriying();
                break;
            case "shichaoban":
                card = new Shichaoban();
                break;
            case "mixingjiao":
                card = new Mixingjiao();
                break;
            case "lunfuyu":
                card = new Lunfuyu();
                break;
            case "yingsewu":
                card = new Yingsewu();
                break;
            case "yinyihui":
                card = new Yinyihui();
                break;
            case "miluri":
                card = new Miluri();
                break;
            case "wulushi":
                card = new Wulushi();
                break;
            case "bitianbanzou":
                card = new Bitianbanzou();
                break;
            case "yinakong":
                card = new Yinakong();
                break;
            case "mingwusheng":
                card = new Mingwusheng();
                break;
            case "qianzaibiaoming":
                card = new Qianzaibiaoming();
                break;
            default:
                card = new FailComposition();
                break;
        }

        if(!(card instanceof FailComposition)){
            card.setRarity(musicRarity);
            //card.setBanner();
            RewardItem musicReward = new MusicReward(card.cardID);
            AbstractDungeon.getCurrRoom().rewards.add(musicReward);
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
        //保存uuid和稀有度的逻辑应该放到reward的里
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

    public boolean cardMatch(String card, String recipe, int rarity) {
        AbstractCard cardObject = CardLibrary.getCard(card);
        if (cardObject == null) return false;

        if(cardObject instanceof BaseCard){
            BaseCard baseCard=(BaseCard) cardObject;
            return baseCard.material.equals(recipe) && baseCard.level >= rarity;
        }
        return false;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        ScreenPostProcessorManager.removePostProcessor(MusicalComposition.postProcessor);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        cardsUsed.clear();
        MaterialScreenProcessor.clear();
        MusicalComposition.isMusicCompositionUsed=false;
    }
}

