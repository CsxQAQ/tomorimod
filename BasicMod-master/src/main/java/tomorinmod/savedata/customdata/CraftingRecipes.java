package tomorinmod.savedata.customdata;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import tomorinmod.cards.WithoutMaterial;
import tomorinmod.savedata.Clearable;
import tomorinmod.savedata.SaveDataInstanceFactory;
import tomorinmod.util.CustomUtils;

import java.util.*;

import static tomorinmod.BasicMod.makeID;

public class CraftingRecipes implements Clearable {

    public HashSet<String> tomorinCards=new HashSet<>();
    public HashMap<String, Integer> musicsCostHashMap =new HashMap<>();
    public HashMap<String,String> cardMaterialHashMap=new HashMap<>();
    public ArrayList<Recipe> recipeArrayList =new ArrayList<>();
    public class Recipe{
        public ArrayList<String> needs=new ArrayList<>();
        public ArrayList<Integer> levels=new ArrayList<>();
        public String music;

        // 默认构造函数（必须有，Gson 需要用到）
        public Recipe() {
        }

        @Override
        public String toString() {
            return "Recipe{" +
                    "needs=" + needs +
                    ", levels=" + levels +
                    ", music='" + music + '\'' +
                    '}';
        }

        public Recipe(String s1, String s2, String s3, int a1, int a2, int a3, String music) {
            this.needs.add(s1);
            this.needs.add(s2);
            this.needs.add(s3);
            this.levels.add(a1);
            this.levels.add(a2);
            this.levels.add(a3);
            this.music = music;
        }

        public int getLevelsSum() {
            return levels.stream().mapToInt(Integer::intValue).sum();
        }

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
            "stone",
            "band",
            "watermelonworm"
    ));

    private List<String> songNames = Arrays.asList(
            "shichaoban", "mixingjiao", "lunfuyu", "yingsewu",
            "yinyihui", "miluri", "wulushi", "bitianbanzou",
            "yinakong", "mingwusheng", "qianzaibiaoming"
    );

    public static final int COMMONCOST_MIN=3;
    public static final int COMMONCOST_MAX=3;
    public static final int UNCOMMONCOST_MIN=4;
    public static final int UNCOMMONCOST_MAX=5;
    public static final int RARECOST_MIN=5;
    public static final int RARECOST_MAX=6;

    public void initializeMusicsCostHashMap() {
        Collections.shuffle(songNames);

        Random random = new Random(); // 创建随机数生成器

        // 分配 commonCost
        for (int i = 0; i < 4; i++) {
            int commonCost = COMMONCOST_MIN + random.nextInt(COMMONCOST_MAX - COMMONCOST_MIN + 1); // 范围内随机数
            musicsCostHashMap.put(songNames.get(i), commonCost);
        }

        // 分配 uncommonCost
        for (int i = 4; i < 8; i++) {
            int uncommonCost = UNCOMMONCOST_MIN + random.nextInt(UNCOMMONCOST_MAX - UNCOMMONCOST_MIN + 1);
            musicsCostHashMap.put(songNames.get(i), uncommonCost);
        }

        // 分配 rareCost
        for (int i = 8; i < 11; i++) {
            int rareCost = RARECOST_MIN + random.nextInt(RARECOST_MAX - RARECOST_MIN + 1);
            musicsCostHashMap.put(songNames.get(i), rareCost);
        }

        musicsCostHashMap.put("chunriying", RARECOST_MAX);
    }

    public String getRandomMaterials(){
        int randomResult = AbstractDungeon.miscRng.random(materials.size()-1);
        return materials.get(randomResult);
    }

    public void initializeCardsMaterials(){
        //for(AbstractCard card : CustomUtils.getAllModCards()){
        for(AbstractCard card : CardLibrary.getAllCards()){
            if(!(card instanceof WithoutMaterial)){
                cardMaterialHashMap.put(card.cardID,getRandomMaterials());
            }
        }
        cardMaterialHashMap.put(makeID("Stone"),"stone");
        cardMaterialHashMap.put(makeID("Band"),"band");
        cardMaterialHashMap.put(makeID("Watermelonworm"),"watermelonworm");

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
        SaveDataInstanceFactory.registerInstance(this);
    }

    public void clear(){
        cardMaterialHashMap.clear();
        recipeArrayList.clear();
        musicsCostHashMap.clear();
    }

    public void generate(){
        initializeMusicsCostHashMap();
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
