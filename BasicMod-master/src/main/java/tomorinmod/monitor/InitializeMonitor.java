package tomorinmod.monitor;

import basemod.interfaces.PostDungeonInitializeSubscriber;
import tomorinmod.savedata.SaveForm;
import tomorinmod.savedata.SaveGifts;
import tomorinmod.savedata.SavePermanentForm;

public class InitializeMonitor implements PostDungeonInitializeSubscriber {

    @Override
    public void receivePostDungeonInitialize() {
        SaveForm.getInstance().clearForm();
        SavePermanentForm.getInstance().clearForm();
        SaveGifts.getInstance().initialize();
    }
}
