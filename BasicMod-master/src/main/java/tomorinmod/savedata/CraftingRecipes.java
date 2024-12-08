package tomorinmod.savedata;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import tomorinmod.util.GetModCardsUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static tomorinmod.BasicMod.makeID;

public class CraftingRecipes {

    public HashSet<String> tomorinCards=new HashSet<>();
    public HashMap<String, Integer> musicsCostHashMap =new HashMap<>();
    public HashMap<String,String> cardMaterialHashMap=new HashMap<>();
    public HashSet<Recipe> recipeHashSet =new HashSet<>();
    class Recipe{
        public ArrayList<String> needs=new ArrayList<>();
        public ArrayList<Integer> levels=new ArrayList<>();
        public String music;

        // 默认构造函数（必须有，Gson 需要用到）
        public Recipe() {
        }

        // 带参数的构造函数
        public Recipe(String s1, String s2, String s3, int a1, int a2, int a3, String music) {
            this.needs.add(s1);
            this.needs.add(s2);
            this.needs.add(s3);
            this.levels.add(a1);
            this.levels.add(a2);
            this.levels.add(a3);
            this.music = music;
        }

        // Getter 和 Setter 方法
        public ArrayList<String> getNeeds() {
            return needs;
        }

        public void setNeeds(ArrayList<String> needs) {
            this.needs = needs;
        }

        public ArrayList<Integer> getLevels() {
            return levels;
        }

        public void setLevels(ArrayList<Integer> levels) {
            this.levels = levels;
        }

        public String getMusic() {
            return music;
        }

        public void setMusic(String music) {
            this.music = music;
        }

        // 重写 toString() 方法，便于调试
        @Override
        public String toString() {
            return "Recipe{" +
                    "needs=" + needs +
                    ", levels=" + levels +
                    ", music='" + music + '\'' +
                    '}';
        }
    }

    private ArrayList<String> materials=new ArrayList<>(Arrays.asList(
            "Stone",
            "Band",
            "Watermelonworm"
    ));

    private final int rareCost=5;
    private final int uncommonCost=4;
    private final int commonCost=3;

    public void initializeMusics() {
        musicsCostHashMap.put("chunriying", rareCost);
        musicsCostHashMap.put("shichaoban", rareCost);
        musicsCostHashMap.put("yinyihui", rareCost);
        musicsCostHashMap.put("mixingjiao", rareCost);
        musicsCostHashMap.put("lunfuyu", uncommonCost);
        musicsCostHashMap.put("yingsewu", uncommonCost);
        musicsCostHashMap.put("miluri", uncommonCost);
        musicsCostHashMap.put("wulushi", uncommonCost);
        musicsCostHashMap.put("yinakong", uncommonCost);
        musicsCostHashMap.put("mingwusheng", commonCost);
        musicsCostHashMap.put("bitianbanzou", commonCost);
        musicsCostHashMap.put("qianzaibiaoming", commonCost);
    }

    public void initializeTomorinCards() {
        tomorinCards.add("Strike");
        tomorinCards.add("Defend");
        tomorinCards.add("Shout");
        tomorinCards.add("StarDust");
        tomorinCards.add("Band");
        tomorinCards.add("Tomotomo");
        tomorinCards.add("WeAreMygo");
        tomorinCards.add("LifelongBand");
        tomorinCards.add("Poem");
        tomorinCards.add("Reversal");
        tomorinCards.add("GravityTomorin");
        tomorinCards.add("StrengthTomorin");
        tomorinCards.add("DarkTomorin");
        tomorinCards.add("ShineTomorin");
        tomorinCards.add("GiftBox");
        tomorinCards.add("TwoFish");
        tomorinCards.add("MusicComposition");
    }

    public String getRandomMaterials(){
        int randomResult = AbstractDungeon.miscRng.random(materials.size()-1);
        return materials.get(randomResult);
    }

    public void initializeCardsMaterials(){
        for(String cardString : tomorinCards){
            cardMaterialHashMap.put(makeID(cardString),getRandomMaterials());
        }
    }

    public boolean recipeAlreadyHave(Recipe aRecipe){
        for(Recipe recipe: recipeHashSet){
            if(recipe.needs.equals(aRecipe.needs)){
                return true;
            }
        }
        return false;
    }

    public void initializeRecipeArrayList(){
        for (Map.Entry<String, Integer> music : musicsCostHashMap.entrySet()) {
            int randomNum1=0;
            int randomNum2=0;
            int randomNum3=0;
            while((randomNum1+randomNum2+randomNum3)!=music.getValue()){
                randomNum1 = AbstractDungeon.miscRng.random(1,4);
                randomNum2 = AbstractDungeon.miscRng.random(1,4);
                randomNum3 = AbstractDungeon.miscRng.random(1,4);
            }

            Recipe recipe=new Recipe(getRandomMaterials(),getRandomMaterials(),getRandomMaterials(),
                    randomNum1,randomNum2,randomNum3,music.getKey());
            while(recipeAlreadyHave(recipe)){
                recipe=new Recipe(getRandomMaterials(),getRandomMaterials(),getRandomMaterials(),
                        randomNum1,randomNum2,randomNum3,music.getKey());
            }
            recipeHashSet.add(recipe);
        }
    }

    // 单例实例
    private static CraftingRecipes instance;

    // 私有化构造函数，防止外部实例化
    private CraftingRecipes() {
        initializeTomorinCards();
        initializeMusics();
    }

    public void generate(){
        cardMaterialHashMap.clear();
        recipeHashSet.clear();
        initializeCardsMaterials();
        initializeRecipeArrayList();
    }

    // 获取单例实例的静态方法
    public static synchronized CraftingRecipes getInstance() {
        if (instance == null) {
            instance = new CraftingRecipes();
        }
        return instance;
    }


}
