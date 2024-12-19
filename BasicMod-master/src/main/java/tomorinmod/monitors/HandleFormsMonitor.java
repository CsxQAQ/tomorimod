package tomorinmod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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

import static tomorinmod.BasicMod.makeID;

public class HandleFormsMonitor extends BaseMonitor implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof MyCharacter) {
            BaseFormCard.clear();
            applyPermanentForms(SavePermanentForm.getInstance().getForms());
        }
    }

    public static void applyPermanentForms(List<BaseFormCard.FormInfo> permanentForms) {
        for (BaseFormCard.FormInfo formInfo : permanentForms) {
            if(BaseFormCard.powerMap.get(formInfo)!=null){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                                BaseFormCard.powerMap.get(formInfo).apply(AbstractDungeon.player), formInfo.amount));

            }
        }
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                BaseFormCard.FormInfo info = permanentForms.get(permanentForms.size() - 1);
                for (AbstractPower power : AbstractDungeon.player.powers) {
                    if (power instanceof BaseFormPower) {
                        BaseFormPower baseFormPower = (BaseFormPower) power;
                        if (baseFormPower.ID.equals(makeID(info.powerName))) {
                            BaseFormPower.changeColor(baseFormPower, "red");
                            BaseFormPower.addDescription(baseFormPower);
                            break;
                        }
                    }
                }
                isDone = true;
            }
        });
    }
}
