package tomorinmod.savedata;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.util.GetModCardsUtils;

import java.util.*;

import static tomorinmod.BasicMod.makeID;

public class CraftingRecipes {

    public static HashSet<String> tomorinCards=new HashSet<>();
    public static HashMap<String, Integer> musicsCostHashMap =new HashMap<>();
    public static HashMap<String,String> cardMaterialHashMap=new HashMap<>();
    public static ArrayList<Recipe> recipeArrayList =new ArrayList<>();
    public class Recipe{
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

        // 计算 levels 的总和
        public int getLevelsSum() {
            return levels.stream().mapToInt(Integer::intValue).sum();
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

    }

    // 静态方法：对列表按 levels 总和排序
    public static void sortByLevelsSum(List<Recipe> recipes) {
        Collections.sort(recipes, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                return Integer.compare(r2.getLevelsSum(), r1.getLevelsSum());
            }
        });
    }

    private ArrayList<String> materials=new ArrayList<>(Arrays.asList(
            "Stone",
            "Band",
            "Watermelonworm"
    ));

    public static final int rareCost=5;
    public static final int uncommonCost=4;
    public static final int commonCost=3;

    public void initializeMusics() {
        musicsCostHashMap.put("chunriying", rareCost);
        musicsCostHashMap.put("shichaoban", rareCost);
        musicsCostHashMap.put("mixingjiao", rareCost);
        musicsCostHashMap.put("lunfuyu", rareCost);
        musicsCostHashMap.put("yingsewu", rareCost);
        musicsCostHashMap.put("yinyihui", uncommonCost);
        musicsCostHashMap.put("miluri", uncommonCost);
        musicsCostHashMap.put("wulushi", uncommonCost);
        musicsCostHashMap.put("bitianbanzou", uncommonCost);
        musicsCostHashMap.put("yinakong", commonCost);
        musicsCostHashMap.put("mingwusheng", commonCost);
        musicsCostHashMap.put("qianzaibiaoming", commonCost);
    }

    public void initializeTomorinCards() {
        ArrayList<AbstractCard> allModCards=GetModCardsUtils.getAllModCards();
        for(AbstractCard abstractCard:allModCards){
            tomorinCards.add(abstractCard.cardID);
        }
    }

    public String getRandomMaterials(){
        int randomResult = AbstractDungeon.miscRng.random(materials.size()-1);
        return materials.get(randomResult);
    }

    public void initializeCardsMaterials(){
        for(String cardString : tomorinCards){
            cardMaterialHashMap.put(cardString,getRandomMaterials());
        }

        cardMaterialHashMap.put(makeID("Stone"),"Stone");
        cardMaterialHashMap.put(makeID("Band"),"Band");
        cardMaterialHashMap.put(makeID("Watermelonworm"),"Watermelonworm");
    }

    public boolean recipeAlreadyHave(Recipe aRecipe){
        for(Recipe recipe: recipeArrayList){
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
            recipeArrayList.add(recipe);
        }
    }

    // 单例实例
    private static CraftingRecipes instance;

    // 私有化构造函数，防止外部实例化
    private CraftingRecipes() {
        initializeTomorinCards();
        initializeMusics();
    }

    public void clear(){
        cardMaterialHashMap.clear();
        recipeArrayList.clear();
    }

    public void generate(){
        initializeCardsMaterials();
        initializeRecipeArrayList();
        sortByLevelsSum(recipeArrayList);
    }

    // 获取单例实例的静态方法
    public static synchronized CraftingRecipes getInstance() {
        if (instance == null) {
            instance = new CraftingRecipes();
        }
        return instance;
    }


}
