package tomorimod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.powers.forms.BaseFormPower;

import static tomorimod.TomoriMod.makeID;
import static tomorimod.util.TextureLoader.getTexture;
import static tomorimod.util.TextureLoader.getTextureNull;

public class FormPowerChangeBlueAction extends AbstractGameAction {

    private String powerName;

    public FormPowerChangeBlueAction(String powerName) {
        this.powerName=powerName;
    }

    public void update() {  //最后一个加入为红，即当前形态
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BaseFormPower) {
                BaseFormPower baseFormPower=(BaseFormPower)power;
                if(!power.ID.equals(makeID(powerName))){
                    BaseFormPower.changeColor(baseFormPower,"green");
                    BaseFormPower.removeDescription(baseFormPower);
                }else{
                    BaseFormPower.changeColor(baseFormPower,"red");
                    BaseFormPower.addDescription(baseFormPower);
                }
            }
        }
        isDone=true;
    }

}
