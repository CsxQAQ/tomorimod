package tomorimod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.cards.customcards.LightAndShadow;

import static tomorimod.TomoriMod.makeID;

public class GravityPower extends BasePower {
    public static final String POWER_ID = makeID(GravityPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public GravityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        //this.amount = amount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            applyEffect();
        }else{
            addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, this.amount,
                    DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void onInitialApplication() {
        if(AbstractDungeon.player.hasPower(makeID("MygoTogetherPower"))){
            LightAndShadow.curAttribute=0;
        }
    }



    public void applyEffect() {

        int[] damageArray = new int[AbstractDungeon.getMonsters().monsters.size()];
        for (int i = 0; i < damageArray.length; i++) {
            damageArray[i] = this.amount;
        }

        addToBot(new DamageAllEnemiesAction(this.owner, damageArray,
                DamageInfo.DamageType.THORNS, DamageAllEnemiesAction.AttackEffect.SLASH_HORIZONTAL));

    }
}
