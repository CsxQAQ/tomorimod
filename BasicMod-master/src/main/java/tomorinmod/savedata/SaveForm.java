package tomorinmod.savedata;

import basemod.abstracts.CustomSavable;

import java.util.ArrayList;

public class SaveForm implements CustomSavable<String>{

    // 单例实例
    private static SaveForm instance;

    // 用于保存表单数据
    private String form = "";

    // 私有化构造函数，防止外部实例化
    private SaveForm() {
    }

    // 获取单例实例的静态方法
    public static synchronized SaveForm getInstance() {
        if (instance == null) {
            instance = new SaveForm();
        }
        return instance;
    }

    // 保存数据
    @Override
    public String onSave() {
        return form; // 返回需要保存的字符串列表
    }

    // 加载数据
    @Override
    public void onLoad(String loadedString) {
        if (loadedString != null) {
            form = loadedString; // 加载并处理数据
        }
    }

    // 提供对 forms 的公共访问方法
    public String getForm() {
        return form;
    }

    public void changeForm(String form) {
        this.form = form;
    }
}
