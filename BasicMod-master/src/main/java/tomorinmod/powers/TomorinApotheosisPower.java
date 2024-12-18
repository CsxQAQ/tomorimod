package tomorinmod.powers;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.customcards.MygoTogether;
import tomorinmod.cards.customcards.TwoFish;

import static tomorinmod.BasicMod.makeID;

public class TomorinApotheosisPower extends BasePower {
    public static final String POWER_ID = makeID(TomorinApotheosisPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TomorinApotheosisPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

}
