package tomorimod.savedata.customdata;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.cards.notshow.SpecialCard;
import tomorimod.character.Tomori;
import tomorimod.configs.TomoriConfig;
import tomorimod.savedata.Clearable;
import tomorimod.savedata.SaveDataInstanceFactory;
import tomorimod.util.CustomUtils;
import tomorimod.util.PlayerUtils;

import java.util.*;

import static tomorimod.TomoriMod.makeID;

public class CraftingRecipes implements Clearable {

    public HashMap<String, Integer> musicsCostHashMap =new HashMap<>();
    public Map<String, Material> cardMaterialHashMap = new HashMap<>();
    public ArrayList<Recipe> recipeArrayList =new ArrayList<>();
    public static class Recipe {
        public ArrayList<Material> needs = new ArrayList<>();
        public ArrayList<Integer> levels = new ArrayList<>();
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

        public Recipe(Material m1, Material m2, Material m3, int a1, int a2, int a3, String music) {
            this.needs.add(m1);
            this.needs.add(m2);
            this.needs.add(m3);
            this.levels.add(a1);
            this.levels.add(a2);
            this.levels.add(a3);
            this.music = music;
        }

        public int getLevelsSum() {
            return levels.stream().mapToInt(Integer::intValue).sum();
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

    private ArrayList<Material> materials=new ArrayList<>(
            Arrays.asList(Material.RED,Material.GREEN,Material.YELLOW));

//    private List<String> songNames = Arrays.asList(
//            "Shichaoban", "Mixingjiao", "Lunfuyu", "Yingsewu",
//            "Yinyihui", "Miluri", "Wulushi", "Bitianbanzou",
//            "Yinakong", "Mingwusheng", "Qianzaibiaoming",
//            "Yeyingran","Ruichengshan","Huifutu"
//
//    );

    private List<String> songNames=new ArrayList<>();

    public void initializeSongNames(){
        for(BaseMusicCard card:CustomUtils.musicCardGroup.values()){
            songNames.add(CustomUtils.idToName(card.cardID));
        }
        songNames.remove("Chunriying");
    }

    public static final int COMMONCOST_MIN=3;
    public static final int COMMONCOST_MAX=3;
    public static final int UNCOMMONCOST_MIN=4;
    public static final int UNCOMMONCOST_MAX=4;
    public static final int RARECOST_MIN=5;
    public static final int RARECOST_MAX=5;

    public void initializeMusicsCostHashMap() {
        Collections.shuffle(songNames);

        Random random = new Random(); // 创建随机数生成器

        // 分配 commonCost
        for (int i = 0; i < 5; i++) {
            int commonCost = COMMONCOST_MIN + random.nextInt(COMMONCOST_MAX - COMMONCOST_MIN + 1); // 范围内随机数
            musicsCostHashMap.put(songNames.get(i), commonCost);
        }

        // 分配 uncommonCost
        for (int i = 5; i < 10; i++) {
            int uncommonCost = UNCOMMONCOST_MIN + random.nextInt(UNCOMMONCOST_MAX - UNCOMMONCOST_MIN + 1);
            musicsCostHashMap.put(songNames.get(i), uncommonCost);
        }

        // 分配 rareCost
        for (int i = 10; i < songNames.size(); i++) {
            int rareCost = RARECOST_MIN + random.nextInt(RARECOST_MAX - RARECOST_MIN + 1);
            musicsCostHashMap.put(songNames.get(i), rareCost);
        }

        musicsCostHashMap.put("Chunriying", RARECOST_MAX);
    }

//    public String getRandomMaterials(){
//        int randomResult = AbstractDungeon.miscRng.random(materials.size()-1);
//        return materials.get(randomResult);
//    }

    public Material getRandomMaterials(){
        int randomResult = AbstractDungeon.miscRng.random(materials.size()-1);
        return materials.get(randomResult);
    }

    public void initializeCardsMaterials(){
        for(AbstractCard card : CardLibrary.getAllCards()){
            if(card.type != AbstractCard.CardType.CURSE && !(card instanceof WithoutMaterial) && card.type != AbstractCard.CardType.STATUS){
                cardMaterialHashMap.put(card.cardID, getRandomMaterials());
            }else{
                cardMaterialHashMap.put(card.cardID, Material.NONE);
            }
        }

        cardMaterialHashMap.put(makeID("Yellow"), Material.YELLOW);
        cardMaterialHashMap.put(makeID("Green"), Material.GREEN);
        cardMaterialHashMap.put(makeID("Red"), Material.RED);
        cardMaterialHashMap.put(makeID("AquariumPass"), Material.AQUARIUMPASS);

        while(cardMaterialHashMap.get(makeID("Strike")).equals(cardMaterialHashMap.get(makeID("Defend")))){
            cardMaterialHashMap.put(makeID("Strike"), getRandomMaterials());
        }

        while(cardMaterialHashMap.get(makeID("Upset")).equals(cardMaterialHashMap.get(makeID("Strike")))
                || cardMaterialHashMap.get(makeID("Upset")).equals(cardMaterialHashMap.get(makeID("Defend")))){
            cardMaterialHashMap.put(makeID("Upset"), getRandomMaterials());
        }

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
                randomNum1 = AbstractDungeon.miscRng.random(1,3);
                randomNum2 = AbstractDungeon.miscRng.random(1,3);
                randomNum3 = AbstractDungeon.miscRng.random(1,3);
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

    @Override
    public void clear(){
        cardMaterialHashMap.clear();
        recipeArrayList.clear();
        musicsCostHashMap.clear();
    }

    public void generate(){
        if(PlayerUtils.isTomori()){
            initializeSongNames();
            initializeMusicsCostHashMap();
            initializeCardsMaterials();
            initializeRecipeArrayList();
            sortByLevelsSum(recipeArrayList);
        }
    }

    // 获取单例实例的静态方法
    public static synchronized CraftingRecipes getInstance() {
        if (instance == null) {
            instance = new CraftingRecipes();
        }
        return instance;
    }


    public enum Material {
        YELLOW("yellow"),
        GREEN("green"),
        RED("red"),
        AQUARIUMPASS("aquariumpass"),
        NONE("");

        private final String value;

        Material(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
