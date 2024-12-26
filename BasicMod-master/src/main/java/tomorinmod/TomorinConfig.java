package tomorinmod;

import basemod.BaseMod;
import basemod.IUIElement;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import static tomorinmod.BasicMod.imagePath;

public class TomorinConfig {

    public static SpireConfig config;
    public static String[] TEXT;
    public static String[] TOOLTIP;
    public static AtomicReference<Boolean> tutorialEnabled;
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

        settingsPanel.addUIElement(enableTutorial);
        Texture badgeTexture = ImageMaster.loadImage(imagePath("badge.png"));
        BaseMod.registerModBadge(badgeTexture, "Tomorinmod", "csx", "", settingsPanel);
    }

    private static SpireConfig makeConfig() {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("tutorial-enabled", String.valueOf(true));
        try {
            return new SpireConfig("SakiTheSpire", "SakiTheSpire-config", defaultProperties);
        } catch (IOException var2) {
            return null;
        }
    }

    public static void update() {
        tutorialEnabled = new AtomicReference<>(config.getBool("tutorial-enabled"));
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
                "启用提示。"
        };
        TOOLTIP = new String[]{
                "提示结束后将自动关闭。"
        };
    }
}
