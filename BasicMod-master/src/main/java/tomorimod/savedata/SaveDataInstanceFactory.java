package tomorimod.savedata;

import java.util.ArrayList;
import java.util.List;

public class SaveDataInstanceFactory {
    private static List<Clearable> instances = new ArrayList<>();

    public static void registerInstance(Clearable instance) {
        instances.add(instance);
    }

    public static void clearAll() {
        for (Clearable instance : instances) {
            instance.clear();
        }
    }
}
