//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class TrueWaitAction extends AbstractGameAction {
    public TrueWaitAction(float setDur) {
        this.setValues((AbstractCreature)null, (AbstractCreature)null, 0);
        this.duration = setDur;

        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}
