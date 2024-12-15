package tomorinmod.savedata.customdata;

import tomorinmod.cards.music.BaseMusicCard;
import tomorinmod.savedata.Clearable;
import tomorinmod.savedata.SaveDataInstanceFactory;

import java.util.HashMap;

//存uuid的，加载存档时把卡牌稀有度复原
public class MusicRarityData implements Clearable {

    private static MusicRarityData instance;

    public HashMap<Integer, BaseMusicCard.MusicRarity> musicRarityHashMap =new HashMap<>();

    private MusicRarityData() {
        SaveDataInstanceFactory.registerInstance(this);
    }

    public static synchronized MusicRarityData getInstance() {
        if (instance == null) {
            instance = new MusicRarityData();
        }
        return instance;
    }

    public void clear(){
        musicRarityHashMap.clear();
    }
}
