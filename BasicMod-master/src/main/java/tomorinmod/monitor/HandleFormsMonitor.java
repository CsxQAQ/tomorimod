package tomorinmod.monitor;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.actions.CheckShineGravityAction;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.*;
import tomorinmod.savedata.SaveForm;
import tomorinmod.savedata.SavePermanentForm;

import java.util.Iterator;

public class HandleFormsMonitor implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof MyCharacter) {
            // form

            switch (SaveForm.getInstance().getForm()){
                case "GravityTomorinPower":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GravityTomorinPower(AbstractDungeon.player), 1));
                    break;
                case "StrengthTomorinPower":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthTomorinPower(AbstractDungeon.player), 1));
                    break;
                case "DarkTomorinPower":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DarkTomorinPower(AbstractDungeon.player), 1));
                    break;
                case "ShineTomorinPower":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ShineTomorinPower(AbstractDungeon.player), 1));
                    break;
                default:
                    break;
            }


            // 永久forms
            Iterator<String> iterator = SavePermanentForm.getInstance().getForms().iterator();

            while (iterator.hasNext()) {
                String form= iterator.next();
                if (form.equals("WeAreMygoPower")) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeAreMygoPower(AbstractDungeon.player), 1));
                }
                if(form.equals("RevolutionPower")){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RevolutionPower(AbstractDungeon.player), 1));
                }
            }
        }
    }
}
