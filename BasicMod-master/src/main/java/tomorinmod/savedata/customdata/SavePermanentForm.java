package tomorinmod.savedata.customdata;

import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.savedata.Clearable;
import tomorinmod.savedata.SaveDataInstanceFactory;

import java.util.ArrayList;

public class SavePermanentForm implements Clearable {

    private static SavePermanentForm instance;

    public ArrayList<BaseFormCard.FormInfo> forms=new ArrayList<>();

    private SavePermanentForm() {
        SaveDataInstanceFactory.registerInstance(this);
    }

    public static synchronized SavePermanentForm getInstance() {
        if (instance == null) {
            instance = new SavePermanentForm();
        }
        return instance;
    }

    // 提供对 forms 的公共访问方法
    public ArrayList<BaseFormCard.FormInfo> getForms() {
        return forms;
    }

    public void clear() {
        forms.clear();
    }
}
