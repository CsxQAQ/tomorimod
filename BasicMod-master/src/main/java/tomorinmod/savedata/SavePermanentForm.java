package tomorinmod.savedata;

import basemod.abstracts.CustomSavable;

import java.util.ArrayList;

public class SavePermanentForm implements CustomSavable<ArrayList<String>>{

    // 单例实例
    private static SavePermanentForm instance;

    // 用于保存表单数据
    private ArrayList<String> forms;

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

    // 保存数据
    @Override
    public ArrayList<String> onSave() {
        return forms; // 返回需要保存的字符串列表
    }

    // 加载数据
    @Override
    public void onLoad(ArrayList<String> loadedStrings) {
        if (loadedStrings != null) {
            forms = new ArrayList<>(loadedStrings); // 加载并处理数据
        }
    }

    // 提供对 forms 的公共访问方法
    public ArrayList<String> getForms() {
        return forms;
    }

    public void addForm(String form) {
        forms.add(form);
    }

    public void clearForm() {
        forms.clear();
    }
}
