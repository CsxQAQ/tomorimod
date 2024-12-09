package tomorinmod.savedata;

import java.util.ArrayList;

public class HistoryCraftRecords {

    // 单例实例
    private static HistoryCraftRecords instance;

    // 用于保存表单数据
    public ArrayList<ArrayList<String>> historyCraftRecords =new ArrayList<>();

    // 私有化构造函数，防止外部实例化
    private HistoryCraftRecords() {
    }

    // 获取单例实例的静态方法
    public static synchronized HistoryCraftRecords getInstance() {
        if (instance == null) {
            instance = new HistoryCraftRecords();
        }
        return instance;
    }

    public void clear(){
        historyCraftRecords.clear();
    }
}
