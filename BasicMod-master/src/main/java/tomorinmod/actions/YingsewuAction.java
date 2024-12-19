package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import tomorinmod.cards.forms.forms.BaseFormCard;
import tomorinmod.monitors.HandleFormsMonitor;

public class YingsewuAction extends AbstractGameAction {

    public YingsewuAction() {

    }

    public void update() {
        //HandleFormsMonitor.applyCurrentFormEffect(BaseFormCard.curForm);
        this.isDone = true;
    }

}
