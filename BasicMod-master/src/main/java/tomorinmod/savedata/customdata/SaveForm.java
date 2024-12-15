package tomorinmod.savedata.customdata;

import tomorinmod.savedata.Clearable;
import tomorinmod.savedata.SaveDataInstanceFactory;

public class SaveForm implements Clearable {

    // 单例实例
    private static SaveForm instance;

    // 用于保存表单数据
    public String form = "";

    // 私有化构造函数，防止外部实例化
    private SaveForm() {
        SaveDataInstanceFactory.registerInstance(this);
    }

    // 获取单例实例的静态方法
    public static synchronized SaveForm getInstance() {
        if (instance == null) {
            instance = new SaveForm();
        }
        return instance;

    }



    public String getForm() {
        return form;
    }

    public void changeForm(String form) {
        this.form = form;
    }

    public void clear(){
        form = "";
    }
}
