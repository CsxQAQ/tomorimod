package tomorinmod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.rare.ConveyFeeling;
import tomorinmod.cards.rare.MygoTogether;
import tomorinmod.cards.uncommon.TwoFish;

import static tomorinmod.BasicMod.makeID;

public class Shine extends BasePower {
    public static final String POWER_ID = makeID("Shine");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public Shine(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount = amount;
        //updateGravityInteraction(); // 初始化时检查并处理 Gravity
    }
    @Override
    public void atStartOfTurn() {
        // 每层Shine恢复1点生命
        flash();
        AbstractPlayer abstractPlayer=AbstractDungeon.player;
        addToBot(new HealAction(this.owner, this.owner, this.amount));

        if(ConveyFeeling.isConveyFeelingUsed){
            int maxHPOverflow = Math.max(0, abstractPlayer.currentHealth + this.amount - abstractPlayer.maxHealth);
            if(maxHPOverflow>0){
                ConveyFeeling.maxHPOverflow=ConveyFeeling.maxHPOverflow+maxHPOverflow;
                abstractPlayer.increaseMaxHp(maxHPOverflow,true);
            }
        }

    }

    @Override
    public void onInitialApplication() {
        if(MygoTogether.isMygoTogetherUsed){
            TwoFish.curAttribute=1;
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
