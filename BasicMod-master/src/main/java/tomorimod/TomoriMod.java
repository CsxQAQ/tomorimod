package tomorimod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.configs.TomoriConfig;
import tomorimod.configs.UnlockedAscension;
import tomorimod.consoles.IncreaseRarityCommon;
import tomorimod.consoles.ShowRecipesCommon;
import tomorimod.events.EndingEvent;
import tomorimod.monitors.*;
import tomorimod.monitors.card.*;
import tomorimod.monsters.anon.AnonMonster;
import tomorimod.monsters.mutsumi.MutsumiMonster;
import tomorimod.monsters.saki.SakiMonster;
import tomorimod.monsters.sakishadow.SakiShadowMonster;
import tomorimod.monsters.taki.TakiMonster;
import tomorimod.monsters.taki.TakiPressureMonitor;
import tomorimod.monsters.DamageNumFrozeMonitor;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.patches.BossGeneratePatch;
import tomorimod.potions.BasePotion;
import tomorimod.powers.*;
import tomorimod.relics.BaseRelic;
import tomorimod.rewards.*;
import tomorimod.savedata.RegisterSaveData;
import tomorimod.screens.NotebookScreen;
import tomorimod.util.KeywordInfo;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import tomorimod.vfx.DynamicBackgroundEffect;
import tomorimod.vfx.DynamicBackgroundTestEffect;

import java.nio.charset.StandardCharsets;
import java.util.*;

//TODO 白祥子
//TODO 卡牌渲染不出来bug又出现了
//TODO 考虑怎么解决relic重复出现的问题 英雄宝典event

@SpireInitializer
public class TomoriMod implements
        AddAudioSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this

    public static void receiveScreen(){
        BaseMod.addCustomScreen(new NotebookScreen());
    }

    public static void receiveMonitor(){
        BaseMod.subscribe(new FormsMonitor());
        BaseMod.subscribe(new InitializeMonitor());
        BaseMod.subscribe(new MusicalCompositionMonitor());
        BaseMod.subscribe(new ConveyFeelingMonitor());
        BaseMod.subscribe(new MiluriMonitor());
        BaseMod.subscribe(new MixingjiaoMonitor());
        BaseMod.subscribe(new PermanentFormsMonitor());
        BaseMod.subscribe(new RemoveCardsFromPoolMonitor());
        BaseMod.subscribe(new TakiPressureMonitor());
        BaseMod.subscribe(new DamageNumFrozeMonitor());
        BaseMod.subscribe(new ShortTermGoalMonitor());
    }

    public static void receiveEvent() {
//        BaseMod.addEvent(new AddEventParams.Builder(SystemEvent.ID, SystemEvent.class)
//                .dungeonID(Exordium.ID).playerClass(Tomori.Meta.TOMORI).create());
        BaseMod.addEvent(new AddEventParams.Builder(EndingEvent.ID, EndingEvent.class)
                .dungeonID(TheEnding.ID).playerClass(Tomori.Meta.TOMORI).create());



    }

    public static void receiveReward(){

        BaseMod.registerCustomReward(
                RewardTypePatch.MUSIC_REWARD,
                (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    return new MusicReward(rewardSave.id);
                },
                (customReward) -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(RewardTypePatch.MUSIC_REWARD.toString(), ((MusicReward)customReward).cardId);
                });

    }


    static {
        loadModInfo();
    }

    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new TomoriMod();

        Tomori.Meta.registerColor();
        Tomori.MetaMusic.registerColor();

        //在这里注册监视器
        receiveMonitor();
    }

    public TomoriMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {

        new AutoAdd(modID)
                .packageFilter(BasePower.class);


        receiveReward();
        receiveScreen();
        receiveEvent();
        //receivePotion();

        receiveMonstor();

        RegisterSaveData.saveData();

        ConsoleCommand.addCommand("music", IncreaseRarityCommon.class);
        ConsoleCommand.addCommand("recipe", ShowRecipesCommon.class);

        TomoriConfig.settingInitialize();
        UnlockedAscension.unlockedAscension();
        //This loads the image used as an icon in the in-game mods menu.
        //Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.

        //If you want to set up a config panel, that will be done here.
        //The Mod Badges page has a basic example of this, but setting up config is overall a bit complex.
        //BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);

        DynamicBackgroundEffect.preloadImages();
        DynamicBackgroundTestEffect.preloadImages();
        //DynamicBackgroundContinueEffect.initializeTexture();



    }

    public void receivePotion() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BasePotion.class) //In the same package as this class
                .any(BasePotion.class, (info, potion) -> { //Run this code for any classes that extend this class
                    //These three null parameters are colors.
                    //If they're not null, they'll overwrite whatever color is set in the potions themselves.
                    //This is an old feature added before having potions determine their own color was possible.
                    BaseMod.addPotion(potion.getClass(), null, null, null, potion.ID, potion.playerClass);
                    //playerClass will make a potion character-specific. By default, it's null and will do nothing.
                });

    }

    private void receiveMonstor() {
        BaseMod.addMonster(AnonMonster.ID, () -> new AnonMonster(0.0F, 0.0F));
        BossGeneratePatch.addBoss("Exordium", AnonMonster.ID, imagePath("monsters/mapicons/") + AnonMonster.class
        .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + AnonMonster.class
        .getSimpleName() + "_map.png");

        BaseMod.addMonster(TakiMonster.ID, () -> new TakiMonster(0.0F, 0.0F));
        BossGeneratePatch.addBoss("TheCity", TakiMonster.ID, imagePath("monsters/mapicons/") + TakiMonster.class
                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + TakiMonster.class
                .getSimpleName() + "_map.png");

        BaseMod.addMonster(MutsumiMonster.ID, () -> new MutsumiMonster(0.0F, 0.0F));
        BossGeneratePatch.addBoss("TheBeyond", MutsumiMonster.ID, imagePath("monsters/mapicons/") + UikaMonster.class
                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + UikaMonster.class
                .getSimpleName() + "_map.png");

        BaseMod.addMonster(UikaMonster.ID, () -> new UikaMonster(0.0F, 0.0F));
        BossGeneratePatch.addBoss("TheBeyond", UikaMonster.ID, imagePath("monsters/mapicons/") + UikaMonster.class
                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + UikaMonster.class
                .getSimpleName() + "_map.png");

//        BaseMod.addMonster(SakiShadowMonster.ID, () -> new SakiShadowMonster(0.0F, 0.0F));
//        BossGeneratePatch.addBoss("TheEnding", SakiShadowMonster.ID, imagePath("monsters/mapicons/") + SakiShadowMonster.class
//                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + SakiShadowMonster.class
//                .getSimpleName() + "_map.png");

        BaseMod.addMonster(SakiMonster.ID, () -> new SakiMonster(0.0F, 0.0F));
        BossGeneratePatch.addBoss("TheEnding", SakiMonster.ID, imagePath("monsters/mapicons/") + SakiShadowMonster.class
                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + SakiShadowMonster.class
                .getSimpleName() + "_map.png");
    }



    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString() {
        return Settings.language.name().toLowerCase();
    }

    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
        BaseMod.loadCustomStringsFile(TutorialStrings.class,
                localizationPath(lang, "TutorialStrings.json"));
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                localizationPath(lang, "MonsterStrings.json"));

    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            } catch (Exception e) {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty()) {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }

    public static String audioPath(String file) {
        return resourcesFolder + "/audios/" + file;
    }

    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }

    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }

    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = TomoriMod.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                    " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                    "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                    "\tat the top of the " + TomoriMod.class.getSimpleName() + " java file.");
        }
        if (!resources.child("images").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "images folder is in the correct location.");
        }
        if (!resources.child("localization").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "localization folder is in the correct location.");
        }

        return name;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(TomoriMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        Tomori.Meta.registerCharacter();
    }

    @Override
    public void receiveEditCards() {

        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .filter(new ClassFilter() {
                    @Override
                    public boolean accept(ClassInfo classInfo, ClassFinder classFinder) {
                        String className = classInfo.getClassName();
                        boolean isSpecialCard=className.contains("notshow");
                        boolean isUikaCard = className.contains("Uika");

                        return !isSpecialCard && !isUikaCard;
                    }
                })
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards

    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });


    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(makeID("tangcry"), audioPath("sounds/tangcry.wav"));
    }
}
