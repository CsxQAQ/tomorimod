package tomorimod.monsters.saki;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import tomorimod.monsters.BaseMonster;
import tomorimod.powers.BasePower;
import tomorimod.util.PlayerUtils;

import static tomorimod.TomoriMod.makeID;

public class SakiHeartWallPower extends BasePower {
    public static final String POWER_ID = makeID(SakiHeartWallPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SakiHeartWallPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
        loadRegion("heartDef");
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > this.amount) {
            addToBot(new DamageAction(AbstractDungeon.player,new SakiDamageInfo(this.owner,damageAmount-this.amount),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            damageAmount = this.amount;
        }
        this.amount -= damageAmount;
        if (this.amount < 0) {
            this.amount = 0;
        }
        return damageAmount;
    }

    public void atStartOfTurn() {
        if(owner instanceof SakiMonster){
            if(((SakiMonster) owner).isHardMode){
                this.amount = SakiMonster.HEART_WALL;
            }else{
                this.amount=SakiMonster.HEART_WALL_WEAK;
            }
        }
    }

    @Override
    public void updateDescription() {
        description=DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
