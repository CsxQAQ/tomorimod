package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class SoyoMultiChangePower extends BasePower {
    public static final String POWER_ID = makeID(SoyoMultiChangePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private String color=null;
    public SoyoMultiChangePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+DESCRIPTIONS[1]+DESCRIPTIONS[2]+DESCRIPTIONS[3];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action){
        if(AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card)!=""){


            if(this.color==null){
                this.color=AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card);
                addToBot(new ApplyPowerAction(owner,owner,new SoyoFormPower(owner,
                        AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card),
                        AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card))));
            }else{
                if(!this.color.equals(AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card))){
                    addToBot(new RemoveSpecificPowerAction(owner,owner,makeID("SoyoFormPower")));
                    this.color=AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card);
                    addToBot(new ApplyPowerAction(owner,owner,new SoyoFormPower(owner,
                            AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card),
                            AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card))));
                }else{
                    addToBot(new ApplyPowerAction(owner,owner,new SoyoFormPower(owner,
                            AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card),
                            AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card))));
                }
            }

        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer){
        if(owner.hasPower(makeID("SoyoFormPower"))){
            addToBot(new RemoveSpecificPowerAction(owner,owner,makeID("SoyoFormPower")));
        }
        this.color=null;
    }



}
