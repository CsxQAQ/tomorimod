package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.cards.notshow.forms.BaseFormCard;
import tomorimod.powers.forms.FormEffect;

import static tomorimod.TomoriMod.makeID;

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
