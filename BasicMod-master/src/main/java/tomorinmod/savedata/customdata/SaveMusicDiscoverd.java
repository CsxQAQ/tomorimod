package tomorinmod.savedata.customdata;

import tomorinmod.savedata.Clearable;
import tomorinmod.savedata.SaveDataInstanceFactory;

import java.util.ArrayList;

public class SaveMusicDiscoverd implements Clearable {

    // 单例实例
    private static SaveMusicDiscoverd instance;

    // 用于保存表单数据
    public ArrayList<String> musicDiscovered= new ArrayList<>();
    public int musicDiscoveredNum=0;

    // 私有化构造函数，防止外部实例化
    private SaveMusicDiscoverd() {
        SaveDataInstanceFactory.registerInstance(this);
    }

    public void musicAdd(String music){
        if(!musicDiscovered.contains(music)){
            musicDiscoveredNum++;
        }
        musicDiscovered.add(music);
    }

    // 获取单例实例的静态方法
    public static synchronized SaveMusicDiscoverd getInstance() {
        if (instance == null) {
            instance = new SaveMusicDiscoverd();
        }
        return instance;
    }

    public void clear(){
        musicDiscovered.clear();
        musicDiscoveredNum=0;
    }
}
