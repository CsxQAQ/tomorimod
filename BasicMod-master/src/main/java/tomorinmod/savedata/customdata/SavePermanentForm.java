package tomorinmod.savedata.customdata;

import tomorinmod.savedata.Clearable;
import tomorinmod.savedata.SaveDataInstanceFactory;

import java.util.ArrayList;

public class SavePermanentForm implements Clearable {

    // 单例实例
    private static SavePermanentForm instance;

    // 用于保存表单数据
    public ArrayList<String> forms=new ArrayList<>();

    // 私有化构造函数，防止外部实例化
    private SavePermanentForm() {
        SaveDataInstanceFactory.registerInstance(this);
    }

    // 获取单例实例的静态方法
    public static synchronized SavePermanentForm getInstance() {
        if (instance == null) {
            instance = new SavePermanentForm();
        }
        return instance;
    }

    // 提供对 forms 的公共访问方法
    public ArrayList<String> getForms() {
        return forms;
    }

    public void clear() {
        forms.clear();
    }
}
