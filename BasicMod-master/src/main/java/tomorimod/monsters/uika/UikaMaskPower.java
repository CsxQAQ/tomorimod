package tomorimod.monsters.uika;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import tomorimod.cards.customcards.ConveyFeeling;
import tomorimod.character.Tomori;
import tomorimod.powers.BasePower;
import tomorimod.util.PlayerUtils;

import static tomorimod.TomoriMod.makeID;

public class UikaMaskPower extends BasePower {
    public static final String POWER_ID = makeID(UikaMaskPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    //public static final int END_TURN=100;
    private boolean isEffected=false;
    private int maskVal;

    public UikaMaskPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        if(AbstractDungeon.player!=null&&AbstractDungeon.player instanceof Tomori){
            maskVal=UikaMonster.MASK_NUM;
        }else{
            maskVal=UikaMonster.MASK_NUM_WEAK;
        }
        loadRegion("time");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {

        amount=maskVal+ PlayerUtils.getPowerNum(makeID("ShinePower"))+
                PlayerUtils.getPowerNum(makeID("GravityPower"));
        isEffected=false;
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+DESCRIPTIONS[1]+amount+DESCRIPTIONS[2];
        if(AbstractDungeon.player!=null&&AbstractDungeon.player instanceof Tomori){
            description+=DESCRIPTIONS[3];
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > this.amount) {
            damageAmount = this.amount;
        }
        this.amount -= damageAmount;
        if (this.amount <= 0) {
            this.amount = 0;
            if(!isEffected){
                playApplyPowerSfx();
                AbstractDungeon.actionManager.callEndTurnEarlySequence();
                CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
                AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
                isEffected=true;
            }
        }

        return damageAmount;
    }

}
