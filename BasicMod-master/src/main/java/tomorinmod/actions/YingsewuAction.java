package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.monitors.HandleFormsMonitor;
import tomorinmod.savedata.customdata.SaveForm;

public class YingsewuAction extends AbstractGameAction {

    public YingsewuAction() {

    }

    public void update() {
        HandleFormsMonitor.applyCurrentFormEffect(SaveForm.getInstance().getForm());
        this.isDone = true;
    }

}
