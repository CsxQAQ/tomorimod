package tomorinmod.savedata;

import basemod.abstracts.CustomSavable;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveGifts implements CustomSavable<ArrayList<Integer>>{

    // 单例实例
    private static SaveGifts instance;

    // 用于保存表单数据
    private ArrayList<Integer> giftGeted;

    // 私有化构造函数，防止外部实例化
    private SaveGifts() {
        initialize();
    }

    // 获取单例实例的静态方法
    public static synchronized SaveGifts getInstance() {
        if (instance == null) {
            instance = new SaveGifts();
        }
        return instance;
    }

    // 保存数据
    @Override
    public ArrayList<Integer> onSave() {
        return giftGeted;
    }

    // 加载数据
    @Override
    public void onLoad(ArrayList<Integer> loadedIntegers) {
        if (loadedIntegers != null) {
            giftGeted = new ArrayList<>(loadedIntegers); // 加载并处理数据
        }
    }

    // 提供对 forms 的公共访问方法
    public ArrayList<Integer> getGiftGeted() {
        return giftGeted;
    }

    public void initialize(){
        giftGeted = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
    }

}
