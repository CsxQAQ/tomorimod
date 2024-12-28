package tomorimod.configs;

import basemod.BaseMod;
import com.megacrit.cardcrawl.helpers.Prefs;
import tomorimod.character.Tomori;

public class UnlockedAscension{
    public static void unlockedAscension() {
        if (TomoriConfig.config.getBool("ascension-unlock")) {
            BaseMod.getModdedCharacters().forEach(character -> {
                if (character instanceof Tomori) {
                    Prefs prefs = character.getPrefs();
                    if (prefs.getInteger("WIN_COUNT", 0) < 20) {
                        prefs.putInteger("WIN_COUNT", 20);
                    }
                    prefs.putBoolean(character.chosenClass.name() + "_WIN", true);
                    prefs.putBoolean("ASCEND_0", true);
                    prefs.putInteger("ASCENSION_LEVEL", 20);
                    prefs.putInteger("LAST_ASCENSION_LEVEL", 20);
                    prefs.flush();
                }
            });
        }
    }
}
