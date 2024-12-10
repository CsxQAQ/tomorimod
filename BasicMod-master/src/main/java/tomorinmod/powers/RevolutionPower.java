package tomorinmod.powers;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tomorinmod.cards.rare.Revolution;

import static tomorinmod.BasicMod.makeID;

public class RevolutionPower extends BasePower {
    public static final String POWER_ID = makeID("RevolutionPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RevolutionPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner,0);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+ Revolution.shines+DESCRIPTIONS[1];
    }

    @Override
    public void onInitialApplication() {
        addToBot(new ApplyPowerAction(this.owner, this.owner,
                new Shine(this.owner, Revolution.shines), Revolution.shines));
    }
}
