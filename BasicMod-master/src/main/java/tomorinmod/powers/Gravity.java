package tomorinmod.powers;

import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.PotionGetHooks;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.rare.MygoTogether;
import tomorinmod.cards.rare.Revolution;
import tomorinmod.cards.uncommon.TwoFish;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class Gravity extends BasePower {
    public static final String POWER_ID = makeID("Gravity");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public Gravity(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount = amount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) return;

        int[] damageArray = new int[AbstractDungeon.getMonsters().monsters.size()];
        for (int i = 0; i < damageArray.length; i++) {
            damageArray[i] = this.amount;
        }
        addToBot(new DamageAllEnemiesAction(this.owner, damageArray, DamageInfo.DamageType.THORNS, DamageAllEnemiesAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void onInitialApplication() {
        if(MygoTogether.isMygoTogetherUsed){
            TwoFish.curAttribute=0;
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
