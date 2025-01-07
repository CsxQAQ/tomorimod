package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorimod.powers.BasePower;
import tomorimod.savedata.customdata.CraftingRecipes;

import javax.xml.soap.Text;
import java.lang.invoke.SwitchPoint;

import static tomorimod.TomoriMod.*;
import static tomorimod.TomoriMod.powerPath;
import static tomorimod.powers.forms.BaseFormPower.getCachedAtlasRegion;

public class SoyoFormPower extends BasePower {
    public static final String POWER_ID = makeID(SoyoFormPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private CraftingRecipes.Material color;
    public static final int RED_NUM=25;
    public static final int GREEN_NUM=10;
    public static final int YELLOW_NUM=1;



    public SoyoFormPower(AbstractCreature owner, int amount, CraftingRecipes.Material color) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.color=color;
        setPowerName();
        loadPowerImgae();
        updateDescription();
    }

    @Override
    public void updateDescription(){
        if(this.color!=null){
            switch (this.color){
                case RED:
                    description=" #y形态 。受到的伤害提高 #b" +RED_NUM*4+ " %，攻击造成的伤害提高 #b" +RED_NUM*amount+" %。";
                    break;
                case GREEN:
                    description=" #y形态 。受到的伤害减少 #b" +GREEN_NUM*(4+amount)+ " %。";
                    break;
                default:
                case YELLOW:
                    description=" #y形态 。回合结束时，获得 #b"+YELLOW_NUM*(4+amount)+" 点 #y力量 。";
                    break;
            }
        }
    }

    public void loadPowerImgae(){
        if(this.color!=null){
            Texture normalTexture = ImageMaster.loadImage(powerPath("soyoform/"+color+".png"));
            Texture hiDefImage = ImageMaster.loadImage(powerPath("soyoform/"+color+"_p.png"));
            this.region48 = getCachedAtlasRegion(normalTexture);
            this.region128 = getCachedAtlasRegion(hiDefImage);
        }
    }

    public void setPowerName(){
        if(this.color!=null){
            switch (color){
                case RED:
                    name="红色";
                    break;
                case GREEN:
                    name="绿色";
                    break;
                default:
                case YELLOW:
                    name="黄色";
                    break;
            }
        }

    }


    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(color.equals(CraftingRecipes.Material.RED)){
            return damage*(2.0f+(RED_NUM/100.0f)*amount);
        }
        return damage;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(color.equals(CraftingRecipes.Material.RED)){
            return damage*2.0f;
        }else if(color.equals(CraftingRecipes.Material.GREEN)){
            return 1-(YELLOW_NUM/100.0f)*(4+amount)<0?0:damage*(1-(YELLOW_NUM/100.0f)*(4+amount));
        }
        return damage;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer){

        if(color.equals(CraftingRecipes.Material.YELLOW)){
            addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner,YELLOW_NUM*(4+amount)),YELLOW_NUM*(4+amount)));
        }
    }

}
