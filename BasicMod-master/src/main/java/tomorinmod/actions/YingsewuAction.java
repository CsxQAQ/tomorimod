package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.forms.forms.BaseFormCard;
import tomorinmod.cards.music.FailComposition;
import tomorinmod.monitors.HandleFormsMonitor;
import tomorinmod.powers.forms.BaseFormPower;
import tomorinmod.powers.forms.FormEffect;

import static tomorinmod.BasicMod.makeID;

public class YingsewuAction extends AbstractGameAction {

    public YingsewuAction() {

    }

    public void update() {
        for(AbstractPower power: AbstractDungeon.player.powers){
            if(power.ID.equals(makeID(BaseFormCard.curForm))){
                if(power instanceof FormEffect){
                    ((FormEffect)power).applyEffectPower();
                }
            }
        }
        this.isDone = true;
    }

}
