package tomorimod.savedata.customdata;

import tomorimod.savedata.Clearable;
import tomorimod.savedata.SaveDataInstanceFactory;

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

    @Override
    public void clear() {
        permanentForms.clear();
    }
}
