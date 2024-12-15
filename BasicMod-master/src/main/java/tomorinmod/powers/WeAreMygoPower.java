package tomorinmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.actions.ApplyShineAction;
import tomorinmod.actions.CheckShineGravityAction;

import static tomorinmod.BasicMod.makeID;

public class WeAreMygoPower extends BasePower {
    public static final String POWER_ID = makeID(WeAreMygoPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public WeAreMygoPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0); // 不使用 amount 作为层数
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) return;
        getWeAreMygoPower();
    }

    public void getWeAreMygoPower(){
        int randomResult = AbstractDungeon.miscRng.random(3);
        AbstractPlayer owner=AbstractDungeon.player;

        switch (randomResult){
            case 0:
                addToBot(new ApplyPowerAction(
                        owner, owner, new PlatedArmorPower(owner, 5), 5));
                break;
            case 1:
                addToBot(new ApplyPowerAction(
                        owner, owner, new IntangiblePlayerPower(owner, 1), 1));
                break;
            case 2:
                addToBot(new ApplyPowerAction(
                        owner, owner, new StrengthPower(owner, 1), 1));
                break;
            case 3:
                addToBot(new ApplyShineAction(1));
                break;
        }
    }



    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
