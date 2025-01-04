package tomorimod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.cards.customcards.ConveyFeeling;
import tomorimod.cards.customcards.LightAndShadow;

import static tomorimod.TomoriMod.makeID;

public class ShinePower extends BasePower {
    public static final String POWER_ID = makeID(ShinePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ShinePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    @Override
    public void atStartOfTurn() {
        if(owner==AbstractDungeon.player){
            flash();
            AbstractPlayer abstractPlayer=AbstractDungeon.player;
            addToBot(new HealAction(this.owner, this.owner, this.amount));

            if(AbstractDungeon.player.hasPower(makeID("ConveyFeelingPower"))){
                int maxHPOverflow = Math.max(0, abstractPlayer.currentHealth + this.amount - abstractPlayer.maxHealth);
                if(maxHPOverflow>0){
                    ConveyFeeling.maxHPOverflow=ConveyFeeling.maxHPOverflow+maxHPOverflow;
                    abstractPlayer.increaseMaxHp(maxHPOverflow,true);
                }
            }
        }
    }

    public void atStartOfMonsterTurn(){
        flash();
        if(owner.hasPower(makeID("ConveyFeelingPower"))){
            owner.maxHealth+=this.amount;
        }
        addToBot(new HealAction(this.owner, this.owner, this.amount));
    }

    @Override
    public void onInitialApplication() {
        if(AbstractDungeon.player.hasPower(makeID("MygoTogetherPower"))){
            LightAndShadow.curAttribute=1;
        }
    }

}
