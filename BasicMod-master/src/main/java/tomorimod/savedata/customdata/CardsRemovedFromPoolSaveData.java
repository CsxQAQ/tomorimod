package tomorimod.savedata.customdata;

import tomorimod.savedata.Clearable;
import tomorimod.savedata.SaveDataInstanceFactory;

import java.util.ArrayList;

public class CardsRemovedFromPoolSaveData implements Clearable {

    private static CardsRemovedFromPoolSaveData instance;

    public ArrayList<String> cardsRemoved =new ArrayList<>();

    private CardsRemovedFromPoolSaveData() {
        SaveDataInstanceFactory.registerInstance(this);
    }

    public static synchronized CardsRemovedFromPoolSaveData getInstance() {
        if (instance == null) {
            instance = new CardsRemovedFromPoolSaveData();
        }
        return instance;
    }

    @Override
    public void clear(){
        cardsRemoved.clear();
    }
}
