package tomorinmod.savedata.customdata;

import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.savedata.Clearable;
import tomorinmod.savedata.SaveDataInstanceFactory;

import java.util.ArrayList;

public class PermanentFormsSaveData implements Clearable {

    private static PermanentFormsSaveData instance;

    public ArrayList<String> permanentForms=new ArrayList<>();

    private PermanentFormsSaveData() {
        SaveDataInstanceFactory.registerInstance(this);
    }

    public static synchronized PermanentFormsSaveData getInstance() {
        if (instance == null) {
            instance = new PermanentFormsSaveData();
        }
        return instance;
    }
    public ArrayList<String> getForms() {
        return permanentForms;
    }

    public void addPermanentForms(String s){
        if(!permanentForms.contains(s)){
            permanentForms.add(s);
        }
    }

    public void clear() {
        permanentForms.clear();
    }
}
