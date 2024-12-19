package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.music.BaseMusicCard;

import static tomorinmod.BasicMod.makeID;

public class AstronomyMinisterPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(AstronomyMinisterPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private int magicNumber;
    private boolean upgraded;

    public AstronomyMinisterPower(AbstractCreature owner,int amount,int magicNumber, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.magicNumber=magicNumber;
        this.upgraded=upgraded;
        this.updateDescription(); //basePower类会在构造方法中调用该方法，
        // 也就是magicNumbber还没初始化updateDescription就被调用了，所以要再调用一遍
    }

    @Override
    public void updateDescription(){
        if(!upgraded){
            description=DESCRIPTIONS[0]+magicNumber+"。";
        }else{
            description=DESCRIPTIONS[0]+magicNumber+"。（已升级）";
        }
    }

    @Override
    public void atStartOfTurn() {
        applyEffectPower();
    }

//    @Override
//    public void applyFormPower() {
//        addToBot(new ApplyPowerAction(AbstractDungeon.player,
//                AbstractDungeon.player, new AstronomyMinisterPower(AbstractDungeon.player,1,magicNumber), 1));
//    }


    @Override
    public void applyEffectPower() {
        addToBot(new ScryAction(magicNumber));
    }
}
