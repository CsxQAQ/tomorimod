package tomorinmod.actions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.powers.GravityPower;
import tomorinmod.powers.forms.BaseFormPower;
import tomorinmod.util.TextureLoader;

import static tomorinmod.BasicMod.makeID;
import static tomorinmod.BasicMod.powerPath;
import static tomorinmod.util.CustomUtils.idToName;
import static tomorinmod.util.TextureLoader.getTexture;
import static tomorinmod.util.TextureLoader.getTextureNull;

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
