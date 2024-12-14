package tomorinmod.savedata;

import tomorinmod.cards.music.BaseMusicCard;

import java.util.HashMap;

public class MusicRarityData {

    private static MusicRarityData instance;

    public HashMap<Integer, BaseMusicCard.MusicRarity> musicRarityHashMap =new HashMap<>();

    private MusicRarityData() {
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
