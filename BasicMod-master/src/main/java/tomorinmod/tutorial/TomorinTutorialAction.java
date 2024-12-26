package tomorinmod.tutorial;

import basemod.ModLabeledToggleButton;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.TomorinConfig;

import java.io.IOException;

public class TomorinTutorialAction extends AbstractGameAction {
    public void update() {
        if (TomorinConfig.config.getBool("tutorial-enabled")) {
            AbstractDungeon.ftue = new TomorinTutorial();
            //TomorinConfig.config.setBool("tutorial-enabled", false);
            try {
                TomorinConfig.config.save();
                ((ModLabeledToggleButton)TomorinConfig.settingsPanel.getUIElements().get(0)).toggle.toggle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.isDone = true;
    }
}
