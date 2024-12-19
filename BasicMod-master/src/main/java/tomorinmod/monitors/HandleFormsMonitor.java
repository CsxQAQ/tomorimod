package tomorinmod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.forms.forms.AstronomyMinister;
import tomorinmod.cards.forms.forms.BaseFormCard;
import tomorinmod.cards.forms.forms.DarkTomorin;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.*;
import tomorinmod.powers.forms.*;
import tomorinmod.savedata.customdata.SavePermanentForm;

import java.util.Map;
import java.util.function.Supplier;

import java.util.HashMap;
import java.util.List;

public class HandleFormsMonitor extends BaseMonitor implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof MyCharacter) {
            BaseFormCard.clear();
            //applyCurrentForm(SaveForm.getInstance().getForm());
            //applyCurrentFormEffect(SaveForm.getInstance().getForm());
            //applyPermanentForms(SavePermanentForm.getInstance().getForms());
        }
    }

//    public static void applyCurrentForm(String form) {
//        FormEffect effect = FormFactory.getFormEffect(form);
//        if (effect != null) {
//            effect.applyFormPower();
//        }
//    }
//
//    public static void applyCurrentFormEffect(String form) {
//        FormEffect effect = FormFactory.getFormEffect(form);
//        if (effect != null) {
//            effect.applyEffectPower();
//        }
//    }

    //private static final Map<Map<String,Integer>, Supplier<AbstractPower>> powerMap = new HashMap<>();


//    static {
//        powerMap.put("AstronomyMinisterPower", () -> new AstronomyMinisterPower(AbstractDungeon.player,1,));
//        powerMap.put("AstronomyMinisterPowerUpgraded", () -> new AstronomyMinisterPowerUpgraded(AbstractDungeon.player,1));
//        powerMap.put("DarkTomorinPower", () -> new DarkTomorinPower(AbstractDungeon.player,1));
//        powerMap.put("DomainExpansionPower", () -> new DomainExpansionPower(AbstractDungeon.player,1));
//        powerMap.put("MascotPower", () -> new MascotPower(AbstractDungeon.player,1));
//        powerMap.put("PantPower", () -> new PantPower(AbstractDungeon.player,1));
//        powerMap.put("PantPowerUpgraded", () -> new PantPowerUpgraded(AbstractDungeon.player,1));
//        powerMap.put("SingerPower", () -> new SingerPower(AbstractDungeon.player,1));
//        powerMap.put("SingerPowerUpgraded", () -> new SingerPowerUpgraded(AbstractDungeon.player,1));
//        powerMap.put("StrengthTomorinPower", () -> new StrengthTomorinPower(AbstractDungeon.player,1));
//
//        powerMap.put("WeAreMygoPower", () -> new WeAreMygoPower(AbstractDungeon.player));
//        powerMap.put("RevolutionPower", () -> new RevolutionPower(AbstractDungeon.player));
//    }

//    public static void applyPermanentForms(List<String> permanentForms) {
//        for (String form : permanentForms) {
//            Supplier<AbstractPower> powerSupplier = powerMap.get(form);
//            if (powerSupplier != null) {
//                AbstractDungeon.actionManager.addToBottom(
//                        new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, powerSupplier.get(), 1)
//                );
//            }
//        }
//    }

}
