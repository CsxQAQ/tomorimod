package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.monitors.HandleFormsMonitor;

public class YingsewuAction extends AbstractGameAction {

    public YingsewuAction() {

    }

    public void update() {
        HandleFormsMonitor.applyCurrentFormEffect(BaseFormCard.curForm);
        this.isDone = true;
    }

}
