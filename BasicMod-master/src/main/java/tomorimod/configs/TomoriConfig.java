package tomorimod.configs;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import static tomorimod.TomoriMod.imagePath;

public class TomoriConfig {

    public static SpireConfig config;
    public static String[] TEXT;
    public static String[] TOOLTIP;
    public static AtomicReference<Boolean> tutorialEnabled;
    public static AtomicReference<Boolean> ascensionUnlocked;
    public static AtomicReference<Boolean> onlyModBossEnabled;

    public static ModPanel settingsPanel;

    public static void settingInitialize() {
        config = makeConfig();
        settingsPanel = new ModPanel();
        update();

        ModLabeledToggleButton enableTutorial = new ModLabeledToggleButton(
                TEXT[0],
                TOOLTIP[0],
                350.0F,
                700.0F,
                Settings.CREAM_COLOR,
                FontHelper.charDescFont,
                tutorialEnabled.get(),
                settingsPanel,
                label -> {},
                button -> {
                    tutorialEnabled.set(button.enabled);
                    config.setBool("tutorial-enabled", tutorialEnabled.get());
                    save();
                }
        );

         ModLabeledToggleButton ascensionUnlock = new ModLabeledToggleButton(
                 TEXT[1],
                 TOOLTIP[1],
                 350.0F,
                 600.0F,
                 Settings.CREAM_COLOR,
                 FontHelper.charDescFont,
                 tutorialEnabled.get(),
                 settingsPanel,
                 label -> {},
                 button -> {
                     tutorialEnabled.set(button.enabled);
                     config.setBool("ascension-unlock", tutorialEnabled.get());
                     save();
                 }
         );

        ModLabeledToggleButton modBossOnly = new ModLabeledToggleButton(
                TEXT[2],
                TOOLTIP[2],
                350.0F,
                500.0F,
                Settings.CREAM_COLOR,
                FontHelper.charDescFont,
                tutorialEnabled.get(),
                settingsPanel,
                label -> {},
                button -> {
                    tutorialEnabled.set(button.enabled);
                    config.setBool("onlyModBoss-enabled", tutorialEnabled.get());
                    save();
                }
        );

        settingsPanel.addUIElement(enableTutorial);
        settingsPanel.addUIElement(ascensionUnlock);
        settingsPanel.addUIElement(modBossOnly);
        Texture badgeTexture = ImageMaster.loadImage(imagePath("badge.png"));
        BaseMod.registerModBadge(badgeTexture, "tomorimod", "csx", "", settingsPanel);
    }

    private static SpireConfig makeConfig() {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("tutorial-enabled", String.valueOf(true));
        defaultProperties.setProperty("ascension-unlock", String.valueOf(false));
        defaultProperties.setProperty("onlyModBoss-enabled", String.valueOf(true));
        try {
            return new SpireConfig("TomoriMod", "Tomori-config", defaultProperties);
        } catch (IOException var2) {
            return null;
        }
    }

    public static void update() {
        tutorialEnabled = new AtomicReference<>(config.getBool("tutorial-enabled"));
        ascensionUnlocked = new AtomicReference<>(config.getBool("ascension-unlock"));
        onlyModBossEnabled = new AtomicReference<>(config.getBool("onlyModBoss-enabled"));
    }

    public static void save() {
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        update();
    }

    static {
        TEXT = new String[]{
                "启用提示。",
                "解锁A20。",
                "仅生成ModBoss。"

        };
        TOOLTIP = new String[]{
                "提示结束后将自动关闭。",
                "重启游戏自动生效。",
                "mod-boss only。"
        };
    }
}
