//package tomorinmod.savedata.customdata;
//
//import tomorinmod.savedata.Clearable;
//import tomorinmod.savedata.SaveDataInstanceFactory;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class SaveGifts implements Clearable {
//
//    // 单例实例
//    private static SaveGifts instance;
//
//    // 用于保存表单数据
//    public ArrayList<Integer> giftGeted=new ArrayList<>(Arrays.asList(0, 0, 0, 0));
//
//    // 私有化构造函数，防止外部实例化
//    private SaveGifts() {
//        SaveDataInstanceFactory.registerInstance(this);
//    }
//
//    // 获取单例实例的静态方法
//    public static synchronized SaveGifts getInstance() {
//        if (instance == null) {
//            instance = new SaveGifts();
//        }
//        return instance;
//    }
//
//    // 提供对 forms 的公共访问方法
//    public ArrayList<Integer> getGiftGeted() {
//        return giftGeted;
//    }
//
//    public void clear(){
//        giftGeted = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
//    }
//
//}
