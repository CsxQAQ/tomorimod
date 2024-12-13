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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.basic.MusicalComposition;
import tomorinmod.cards.music.*;
import tomorinmod.powers.Shine;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.savedata.HistoryCraftRecords;
import tomorinmod.savedata.SaveMusicDiscoverd;
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
                if(!baseCard.material.equals("")){
                    MaterialScreenProcessor.drawImage(baseCard.material);
                    cardsUsed.add(abstractCard.cardID);
                }

                if(cardsUsed.size()==3){
                    addHistoryRecipes();
                    MusicalComposition.isMusicCompositionUsed=false;
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, Shine.POWER_ID));
                    MaterialScreenProcessor.clear();
                }
            }
        }
    }

    public void addHistoryRecipes(){

        String music = matchRecipe();

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
        getMusic(music);
        if (!"fail".equals(music)) {
            SaveMusicDiscoverd.getInstance().musicDiscovered.add(music);
        }
    }

    public void getMusic(String music){
        switch(music){
            case "chunriying":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Chunriying(), 1));
                break;
            case "shichaoban":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shichaoban(), 1));
                break;
            case "mixingjiao":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Mixingjiao(), 1));
                break;
            case "lunfuyu":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lunfuyu(), 1));
                break;
            case "yingsewu":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Yingsewu(), 1));
                break;
            case "yinyihui":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Yinyihui(), 1));
                break;
            case "miluri":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Miluri(), 1));
                break;
            case "wulushi":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Wulushi(), 1));
                break;
            case "bitianbanzou":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Bitianbanzou(), 1));
                break;
            case "yinakong":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Yinakong(), 1));
                break;
            case "mingwusheng":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Mingwusheng(), 1));
                break;
            case "qianzaibiaoming":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Qianzaibiaoming(), 1));
                break;
            default:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FailComposition(), 1));
                break;
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
