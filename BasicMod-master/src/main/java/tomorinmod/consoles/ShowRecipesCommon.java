package tomorinmod.consoles;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import tomorinmod.cards.music.BaseMusicCard;
import tomorinmod.savedata.customdata.CraftingRecipes;

public class ShowRecipesCommon extends ConsoleCommand {
    public ShowRecipesCommon() {
        maxExtraTokens = 1;
        minExtraTokens = 0;
        requiresPlayer = false;
    }

    @Override
    protected void execute(String[] tokens, int i) {
        if(tokens.length==1){
            for(CraftingRecipes.Recipe recipe:CraftingRecipes.getInstance().recipeArrayList){
                DevConsole.log(recipe.toString());
            }
        }else{
            int index=ConvertHelper.tryParseInt(tokens[1]);
            DevConsole.log(CraftingRecipes.getInstance().recipeArrayList.get(index).toString());
        }


    }
}
