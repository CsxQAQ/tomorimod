package tomorinmod.savedata;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.abstracts.CustomSavableRaw;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class SavePermanentForm {

    // 单例实例
    private static SavePermanentForm instance;

    // 用于保存表单数据
    public ArrayList<String> forms;

    // 私有化构造函数，防止外部实例化
    private SavePermanentForm() {
        forms = new ArrayList<>();
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
