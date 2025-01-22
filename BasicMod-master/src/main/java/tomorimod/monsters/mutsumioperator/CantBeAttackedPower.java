package tomorimod.monsters.mutsumioperator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.monsters.saki.SakiDamageInfo;
import tomorimod.monsters.saki.SakiMonster;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class CantBeAttackedPower extends BasePower {
    public static final String POWER_ID = makeID(CantBeAttackedPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public CantBeAttackedPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner,0);
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return 0;
    }

}
