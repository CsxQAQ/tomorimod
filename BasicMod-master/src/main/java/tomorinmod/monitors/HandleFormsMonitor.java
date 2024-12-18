package tomorinmod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.actions.ApplyGravityAction;
import tomorinmod.actions.ApplyShineAction;
import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.*;
import tomorinmod.powers.forms.*;
import tomorinmod.savedata.customdata.SaveForm;
import tomorinmod.savedata.customdata.SavePermanentForm;

import java.util.List;

public class HandleFormsMonitor extends BaseMonitor implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof MyCharacter) {
            BaseFormCard.clear();
            applyCurrentForm(SaveForm.getInstance().getForm());
            applyCurrentFormEffect(SaveForm.getInstance().getForm());
            applyPermanentForms(SavePermanentForm.getInstance().getForms());
        }
    }

    public static void applyCurrentForm(String form) {
        FormEffect effect = FormFactory.getFormEffect(form);
        if (effect != null) {
            effect.applyFormPower();
        }
    }

    public static void applyCurrentFormEffect(String form) {
        FormEffect effect = FormFactory.getFormEffect(form);
        if (effect != null) {
            effect.applyEffectPower();
        }
    }

    public static void applyPermanentForms(List<String> permanentForms) {
        for (String form : permanentForms) {
            if (form.equals("WeAreMygoPower")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeAreMygoPower(AbstractDungeon.player), 1));
            }
            if (form.equals("RevolutionPower")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RevolutionPower(AbstractDungeon.player), 1));
            }
        }
    }

}
