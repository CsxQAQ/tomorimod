package tomorinmod.powers;

import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.actions.BigGirlsBandEraAction;
import tomorinmod.cards.MygoTogether;
import tomorinmod.cards.TwoFish;

import static tomorinmod.BasicMod.makeID;

public class BigGirlsBandEraPower extends BasePower{
    public static final String POWER_ID = makeID("BigGirlsBandEraPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private boolean isUpgraded;

    public BigGirlsBandEraPower(AbstractCreature owner,boolean isUpgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        this.isUpgraded= isUpgraded;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new BigGirlsBandEraAction(isUpgraded));
    }
}
