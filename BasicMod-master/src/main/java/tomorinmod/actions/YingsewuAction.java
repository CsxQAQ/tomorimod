package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.powers.*;
import tomorinmod.savedata.SaveForm;

import java.util.Iterator;
import java.util.UUID;

public class YingsewuAction extends AbstractGameAction {

    public YingsewuAction() {

    }

    public void update() {
        switch (SaveForm.getInstance().getForm()) {
            case "GravityTomorinPower":
                addToBot(new ApplyGravityAction(4));
                break;
            case "StrengthTomorinPower":
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 4), 4));
                break;
            case "DarkTomorinPower":
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(AbstractDungeon.player, 1, true), 1));
                break;
            case "ShineTomorinPower":
                addToBot(new ApplyShineAction(3));
                break;
            default:
                break;
        }
        this.isDone = true;
    }

}
