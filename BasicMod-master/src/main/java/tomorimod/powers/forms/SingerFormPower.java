package tomorimod.powers.forms;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.music.BaseMusicCard;

import static tomorimod.TomoriMod.makeID;

public class SingerFormPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(SingerFormPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public SingerFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        //this.amount=amount;
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+" 张牌。";
        super.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof BaseMusicCard){
            applyEffectPower();
        }
    }

    @Override
    public void applyEffectPower() {
        flash();
        addToBot(new DrawCardAction(AbstractDungeon.player, amount));
    }
}
