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

    public void update() {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BaseFormPower) {
                Texture normalTexture;
                Texture hiDefImage;
                if(!power.ID.equals(makeID(powerName))){
                    normalTexture = TextureLoader.getPowerTexture(idToName(power.ID));
                    hiDefImage = TextureLoader.getHiDefPowerTexture(idToName(power.ID));
                }else{
                    normalTexture = getTexture(powerPath("red/output32/"+idToName(power.ID)+"_red.png"));
                    hiDefImage = getTextureNull(powerPath("red/output84/"+idToName(power.ID)+"_red.png"));
                }
                if(normalTexture!=null){
                    power.region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
                }
                if(hiDefImage!=null){
                    power.region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                }
                power.updateDescription();
            }
        }
        isDone=true;
    }

}
