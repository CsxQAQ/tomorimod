package tomorimod.tutorial;

import basemod.ModLabeledToggleButton;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.TomoriConfig;

import java.io.IOException;

public class TomoriTutorialAction extends AbstractGameAction {
    public void update() {
        if (TomoriConfig.config.getBool("tutorial-enabled")) {
            AbstractDungeon.ftue = new TomoriTutorial();
            //TomoriConfig.config.setBool("tutorial-enabled", false);
            try {
                TomoriConfig.config.save();
                ((ModLabeledToggleButton) TomoriConfig.settingsPanel.getUIElements().get(0)).toggle.toggle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.isDone = true;
    }
}
