package tomorimod.consoles;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import tomorimod.savedata.customdata.CraftingRecipes;

public class ShowRecipesCommon extends ConsoleCommand {
    public ShowRecipesCommon() {
        maxExtraTokens = 1;
        minExtraTokens = 0;
        requiresPlayer = false;
    }

    @Override
    protected void execute(String[] tokens, int i) {
        try {
            // 如果只输入了 recipe，不带参数，则打印全部
            if (tokens.length == 1) {
                for (CraftingRecipes.Recipe recipe : CraftingRecipes.getInstance().recipeArrayList) {
                    DevConsole.log(recipe.toString());
                }
            } else {
                // 有附加参数时，先尝试将其当做整数解析
                int index = ConvertHelper.tryParseInt(tokens[1], -1);
                // 如果 index != -1，则表示确实能转换为数字
                if (index != -1) {
                    if (index < 0 || index >= CraftingRecipes.getInstance().recipeArrayList.size()) {
                        DevConsole.log("Error: Index out of bounds. Valid range is 0 to "
                                + (CraftingRecipes.getInstance().recipeArrayList.size() - 1) + ".");
                        return;
                    }
                    // 打印对应索引的 Recipe
                    DevConsole.log(CraftingRecipes.getInstance().recipeArrayList.get(index).toString());
                } else {
                    // 否则把 tokens[1] 当作音乐名称（music 的 string）进行搜索
                    String musicName = tokens[1];
                    boolean found = false;

                    for (CraftingRecipes.Recipe recipe : CraftingRecipes.getInstance().recipeArrayList) {
                        if (recipe.getMusic().equals(musicName)) {
                            DevConsole.log(recipe.toString());
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        DevConsole.log("Error: No recipe found for music: " + musicName);
                    }
                }
            }
        } catch (Exception e) {
            DevConsole.log("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}