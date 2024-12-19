//package tomorinmod.savedata.customdata;
//
//import tomorinmod.cards.forms.forms.BaseFormCard;
//import tomorinmod.savedata.Clearable;
//import tomorinmod.savedata.SaveDataInstanceFactory;
//
//import java.util.ArrayList;
//
//public class BaseFormCardSaveData implements Clearable {
//
//    private static BaseFormCardSaveData instance;
//
//    public String form=null;
//
//    private BaseFormCardSaveData() {
//        SaveDataInstanceFactory.registerInstance(this);
//    }
//
//    public static synchronized BaseFormCardSaveData getInstance() {
//        if (instance == null) {
//            instance = new BaseFormCardSaveData();
//        }
//        return instance;
//    }
//
//    // 提供对 forms 的公共访问方法
//    public String getForms() {
//        return form;
//    }
//
//    public void clear() {
//        form=null;
//    }
//}
