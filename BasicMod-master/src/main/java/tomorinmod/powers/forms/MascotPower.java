package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class MascotPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(MascotPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public MascotPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    private boolean isEffected=false;

    @Override
    public void atStartOfTurn() {
        if(!isEffected){
            applyEffectPower();
            isEffected=true;
            this.description = CardCrawlGame.languagePack.getPowerStrings(ID).DESCRIPTIONS[0]+"（ #y已生效 ）";
            //this.updateDescription();
        }
    }

    @Override
    public void applyFormPower() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MascotPower(AbstractDungeon.player), 1));
    }

    @Override
    public void applyEffectPower() {
        addToBot(new GainEnergyAction(2));
    }
}
