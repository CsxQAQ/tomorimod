package tomorinmod.powers.forms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import tomorinmod.powers.BasePower;
import tomorinmod.util.TextureLoader;

import static tomorinmod.BasicMod.makeID;
import static tomorinmod.BasicMod.powerPath;
import static tomorinmod.util.CustomUtils.idToName;
import static tomorinmod.util.TextureLoader.getTexture;
import static tomorinmod.util.TextureLoader.getTextureNull;

public class BaseFormPower extends BasePower {

    public BaseFormPower(String powerID,PowerType type,boolean turnBased,AbstractCreature owner,int amount) {
        super(powerID, type, turnBased, owner, amount);
    }

    @Override
    public void onRemove(){
        if(AbstractDungeon.player.hasRelic(makeID("MicrophoneRelic"))){
            if(this instanceof FormEffect){
                ((FormEffect)this).applyEffectPower();
            }
        }
    }

    public static void changeColor(BaseFormPower power,String color){
        Texture normalTexture;
        Texture hiDefImage;
        if(color.equals("green")){
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
