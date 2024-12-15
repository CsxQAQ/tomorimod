package tomorinmod.savedata;

import java.util.ArrayList;

public class SaveMusicReward {

    // 单例实例
    private static SaveMusicReward instance;

    // 用于保存表单数据
    public String cardId=null;

    // 私有化构造函数，防止外部实例化
    private SaveMusicReward() {
    }

    // 获取单例实例的静态方法
    public static synchronized SaveMusicReward getInstance() {
        if (instance == null) {
            instance = new SaveMusicReward();
        }
        return instance;

    }

    public void clear(){
        cardId=null;
    }
}
