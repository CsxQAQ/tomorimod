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
import tomorimod.cards.music.*;
import tomorimod.cards.notshow.FailComposition;
import tomorimod.effects.MaterialUiDelayClearAction;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.powers.custompowers.MusicCompositionPower;
import tomorimod.rewards.MusicReward;
import tomorimod.savedata.customdata.CraftingRecipes;
import tomorimod.savedata.customdata.HistoryCraftRecords;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;
import tomorimod.screens.MaterialScreenProcessor;
import tomorimod.ui.MaterialUi;
import tomorimod.util.CustomUtils;

import java.util.ArrayList;
import java.util.List;

import static tomorimod.TomoriMod.makeID;

public class MusicalCompositionMonitor extends BaseMonitor implements OnCardUseSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, PostUpdateSubscriber {

    public static final ArrayList<AbstractCard> cardsUsed = new ArrayList<>(3);

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(AbstractDungeon.player.hasPower(makeID("MusicCompositionPower"))){
        //if(MusicComposition.isMusicCompositionUsed){

            if(!AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(abstractCard).equals(CraftingRecipes.Material.NONE)){
                MaterialUi.getInstance().setMaterial(
                    AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(abstractCard),
                        AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(abstractCard));
                cardsUsed.add((abstractCard));
            }
            if(cardsUsed.size()==3){
                String music = matchRecipe();
                getMusic(music); //先getMusic是因为要先判断是否已创作过music来决定reward
                addHistoryRecipes(music);
                //MusicComposition.isMusicCompositionUsed=false;

                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction
                        (AbstractDungeon.player, AbstractDungeon.player, MusicCompositionPower.POWER_ID));
                AbstractDungeon.actionManager.addToBottom(new MaterialUiDelayClearAction());
                cardsUsed.clear();
            }
        }
    }

    public void getMusic(String music){
        BaseMusicCard.MusicRarity musicRarity= BaseMusicCard.getMusicRarityByCost(music);
        BaseMusicCard card;

        if(music.equals("fail")){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FailComposition(), 1));
            return;
        }

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
        CraftingRecipes.Recipe records=new CraftingRecipes.Recipe();

        //ArrayList<String> records = new ArrayList<>(4);
        for (AbstractCard card : cardsUsed) {
            records.needs.add(CraftingRecipes.getInstance().cardMaterialHashMap.get(card.cardID));
            records.levels.add(AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card));
        }
        records.music=music;
        HistoryCraftRecords.getInstance().historyCraftRecords.add(records);

        if (!"fail".equals(music)) {
            SaveMusicDiscoverd.getInstance().musicAdd(music);
        }
    }


    private String matchRecipe() {
        List<String> matchedMusics = new ArrayList<>();

        for (CraftingRecipes.Recipe recipe : CraftingRecipes.getInstance().recipeArrayList) {
            if (isRecipeMatched(recipe)) {
                matchedMusics.add(recipe.music);
            }
        }

        if (matchedMusics.isEmpty()) {
            return "fail";
        }
        int randomResult = AbstractDungeon.miscRng.random(matchedMusics.size()-1);
        return matchedMusics.get(randomResult);
    }

    private boolean isRecipeMatched(CraftingRecipes.Recipe recipe) {
        for (int i = 0; i < 3; i++) {
            if (!cardMatch(cardsUsed.get(i), recipe.needs.get(i), recipe.levels.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean cardMatch(AbstractCard card, CraftingRecipes.Material recipe, int rarity) {
        if(AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card).equals(CraftingRecipes.Material.AQUARIUMPASS)){
            return AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card) >= rarity;
        }
        return AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card).equals(recipe) &&
                AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card) >= rarity;
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
        cardsUsed.clear();
        MaterialUi.getInstance().clear();
    }
}

