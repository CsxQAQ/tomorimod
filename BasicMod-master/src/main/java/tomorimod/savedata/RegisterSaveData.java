package tomorimod.savedata;

import basemod.BaseMod;
import basemod.abstracts.CustomSavableRaw;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import tomorimod.cards.forms.BaseFormCard;
import tomorimod.cards.music.Chunriying;
import tomorimod.savedata.customdata.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterSaveData {

    public RegisterSaveData(){

    }
    public static void saveData() {


        BaseMod.addSaveField("FormsSaveData", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(FormsSaveData.getInstance().forms);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    FormsSaveData.getInstance().forms = gson.fromJson(jsonElement, new TypeToken<ArrayList<BaseFormCard.FormInfo>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("PermanentFormsSaveData", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(PermanentFormsSaveData.getInstance().permanentForms);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    PermanentFormsSaveData.getInstance().permanentForms = gson.fromJson(jsonElement, new TypeToken<ArrayList<String>>() {}.getType());
                }
            }
        });

//        BaseMod.addSaveField("SaveGifts", new CustomSavableRaw() {
//            private final Gson gson = new Gson();
//
//            @Override
//            public JsonElement onSaveRaw() {
//                return gson.toJsonTree(SaveGifts.getInstance().giftGeted);
//            }
//
//            @Override
//            public void onLoadRaw(JsonElement jsonElement) {
//                if (jsonElement != null) {
//                    SaveGifts.getInstance().giftGeted = gson.fromJson(jsonElement, new TypeToken<ArrayList<Integer>>() {}.getType());
//                }
//            }
//        });

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

        BaseMod.addSaveField("recipeArrayList", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(CraftingRecipes.getInstance().recipeArrayList);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    CraftingRecipes.getInstance().recipeArrayList = gson.fromJson(jsonElement, new TypeToken<ArrayList<CraftingRecipes.Recipe>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("musicCostHashMap", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(CraftingRecipes.getInstance().musicsCostHashMap);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    CraftingRecipes.getInstance().musicsCostHashMap = gson.fromJson(jsonElement, new TypeToken<HashMap<String,Integer>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("HistoryCraftRecords", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(HistoryCraftRecords.getInstance().historyCraftRecords);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    HistoryCraftRecords.getInstance().historyCraftRecords = gson.fromJson(jsonElement, new TypeToken<ArrayList<ArrayList<String>>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("MusicDiscoveredArrayList", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(SaveMusicDiscoverd.getInstance().musicDiscovered);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    SaveMusicDiscoverd.getInstance().musicDiscovered = gson.fromJson(jsonElement, new TypeToken<ArrayList<String>>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("MusicDiscoveredNum", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(SaveMusicDiscoverd.getInstance().musicDiscoveredNum);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    SaveMusicDiscoverd.getInstance().musicDiscoveredNum = gson.fromJson(jsonElement, new TypeToken<Integer>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("chunriyingisIntensify", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(Chunriying.isIntensify);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    Chunriying.isIntensify = gson.fromJson(jsonElement, new TypeToken<Boolean>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("BaseFormCardCurForm", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(BaseFormCard.curForm);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    BaseFormCard.curForm = gson.fromJson(jsonElement, new TypeToken<String>() {}.getType());
                }
            }
        });

        BaseMod.addSaveField("CardsRemovedFromPoolSaveData", new CustomSavableRaw() {
            private final Gson gson = new Gson();

            @Override
            public JsonElement onSaveRaw() {
                return gson.toJsonTree(CardsRemovedFromPoolSaveData.getInstance().cardsRemoved);
            }

            @Override
            public void onLoadRaw(JsonElement jsonElement) {
                if (jsonElement != null) {
                    CardsRemovedFromPoolSaveData.getInstance().cardsRemoved = gson.fromJson(jsonElement, new TypeToken<ArrayList<String>>() {}.getType());
                }
            }
        });

    }
}
