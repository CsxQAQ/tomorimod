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

    private int magicNumber;
    private boolean isEffected=false;

    public MascotPower(AbstractCreature owner,int amount,int magicNumber) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.magicNumber=magicNumber;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if(!isEffected){
            applyEffectPower();
            isEffected=true;
            this.description = DESCRIPTIONS[0]+"（ #y已生效 ）";
        }
    }

//    @Override
//    public void applyFormPower() {
//        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MascotPower(AbstractDungeon.player,1), 1));
//    }

    @Override
    public void applyEffectPower() {
        addToBot(new GainEnergyAction(2));
    }
}
