package tomorinmod.monitors;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.powers.WeAreMygoPower;
import tomorinmod.powers.custompowers.StarDustPower;
import tomorinmod.powers.forms.BaseFormPower;
import tomorinmod.powers.permanentforms.SmallMonmentPower;
import tomorinmod.savedata.customdata.FormsSaveData;
import tomorinmod.savedata.customdata.PermanentFormsSaveData;

import java.util.List;

import static tomorinmod.BasicMod.makeID;

public class PermanentFormsMonitor extends BaseMonitor implements OnStartBattleSubscriber {
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        applyPermanentForms();
    }

    public static void applyPermanentForms() {
        AbstractPlayer p=AbstractDungeon.player;
        for(String permantForms: PermanentFormsSaveData.getInstance().permanentForms){
            switch (permantForms){
                case "SmallMonment":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SmallMonmentPower(p),0));
                    break;
                case "StarDust":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StarDustPower(p),0));
                    break;
                case "WeAreMygo":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new WeAreMygoPower(p),0));
                    break;
            }
        }
    }
}
