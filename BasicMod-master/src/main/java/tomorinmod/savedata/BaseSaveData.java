package tomorinmod.savedata;

import basemod.BaseMod;
import basemod.abstracts.CustomSavableRaw;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import tomorinmod.cards.BaseCard;
import tomorinmod.monitor.InitializeMonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BaseSaveData {

    public BaseSaveData(){

    }
    public static void saveData() {
        // 注册字段
        BaseMod.addSaveField("SaveForm", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(SaveForm.getInstance().form);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    SaveForm.getInstance().form = jsonElement.getAsString();
                }
            }
        });

        BaseMod.addSaveField("SavePermanentForm", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(SavePermanentForm.getInstance().forms);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    SavePermanentForm.getInstance().forms = gson.fromJson(jsonElement, new TypeToken<ArrayList<String>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("SaveGifts", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(SaveGifts.getInstance().giftGeted);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    SaveGifts.getInstance().giftGeted = gson.fromJson(jsonElement, new TypeToken<ArrayList<Integer>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("cardMaterialHashMap", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(CraftingRecipes.getInstance().cardMaterialHashMap);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    CraftingRecipes.getInstance().cardMaterialHashMap = gson.fromJson(jsonElement, new TypeToken<HashMap<String,String>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("recipeHashSet", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(CraftingRecipes.getInstance().recipeHashSet);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    CraftingRecipes.getInstance().recipeHashSet = gson.fromJson(jsonElement, new TypeToken<HashSet<CraftingRecipes.Recipe>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("isInitialized", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(InitializeMonitor.isInitialized);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    InitializeMonitor.isInitialized = gson.fromJson(jsonElement, new TypeToken<Boolean>() {}.getType());
                }
            }
        });
    }
}
