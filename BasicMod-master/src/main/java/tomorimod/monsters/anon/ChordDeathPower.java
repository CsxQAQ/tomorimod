package tomorimod.monsters.anon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.powers.BasePower;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class ChordDeathPower extends BasePower {
    public static final String POWER_ID = makeID(ChordDeathPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;



    public ChordDeathPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void onDeath(){
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m instanceof AnonMonster && !m.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(m,owner,new AnonLittlePractisePower(m,1),1));
            }
        }
    }
}
