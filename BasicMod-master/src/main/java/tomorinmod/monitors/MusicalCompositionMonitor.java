package tomorinmod.monitors;

import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.basic.MusicalComposition;
import tomorinmod.cards.music.*;
import tomorinmod.cards.special.FailComposition;
import tomorinmod.effects.MaterialUiDelayClearAction;
import tomorinmod.patches.AbstractCardSetMaterialPatch;
import tomorinmod.powers.InCompositionPower;
import tomorinmod.rewards.MusicReward;
import tomorinmod.savedata.customdata.CraftingRecipes;
import tomorinmod.savedata.customdata.HistoryCraftRecords;
import tomorinmod.savedata.customdata.SaveMusicDiscoverd;
import tomorinmod.screens.MaterialScreenProcessor;
import tomorinmod.ui.MaterialUi;

import java.util.ArrayList;

public class MusicalCompositionMonitor extends BaseMonitor implements OnCardUseSubscriber, OnStartBattleSubscriber, PostBattleSubscriber {

    public static final ArrayList<AbstractCard> cardsUsed = new ArrayList<>(3);

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(MusicalComposition.isMusicCompositionUsed){

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
                MusicalComposition.isMusicCompositionUsed=false;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, InCompositionPower.POWER_ID));
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
                break;
        }

        if (card != null) {
            card.setRarity(musicRarity);
            if(!SaveMusicDiscoverd.getInstance().musicDiscovered.contains(music)){
                RewardItem musicReward = new MusicReward(card.cardID);
                AbstractDungeon.getCurrRoom().rewards.add(musicReward);
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
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
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        //ScreenPostProcessorManager.addPostProcessor(MaterialScreenProcessor.getInstance());
        cardsUsed.clear();
        MaterialUi.getInstance().clear();
        MusicalComposition.isMusicCompositionUsed=false;
    }
}

