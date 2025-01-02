package tomorimod.patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

import static tomorimod.TomoriMod.audioPath;

@SpirePatch(clz = TempMusic.class, method = "getSong")
public class MusicPatch {
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        for (MusicHelper value : MusicHelper.values()) {
            if (value.name().equals(key)) {
                return SpireReturn.Return(MainMusic.newMusic(value.path()));
            }
        }
        return SpireReturn.Continue();
    }

    public enum MusicHelper {
        BITIANBANZOU(audioPath("musics/bitianbanzou.ogg")),
        MIXINGJIAO(audioPath("musics/mixingjiao.ogg")),
        KILLKISS(audioPath("musics/killkiss.ogg"));


        private final String path;

        MusicHelper(String path) {
            this.path = path;
        }

        public String path() {
            return this.path;
        }
    }
}

