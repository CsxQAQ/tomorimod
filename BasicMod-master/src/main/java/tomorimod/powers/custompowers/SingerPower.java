package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.powers.BasePower;
import tomorimod.powers.forms.BaseFormPower;
import tomorimod.powers.forms.FormEffect;

import static tomorimod.TomoriMod.makeID;

public class SingerPower extends BasePower {
    public static final String POWER_ID = makeID(SingerPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public SingerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        //this.amount=amount;
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+" 张牌。";
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof BaseMusicCard){
            flash();
            addToBot(new DrawCardAction(AbstractDungeon.player, amount));
        }
    }

}
