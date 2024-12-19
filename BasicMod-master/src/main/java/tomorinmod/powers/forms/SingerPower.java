package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.music.BaseMusicCard;

import static tomorinmod.BasicMod.makeID;

public class SingerPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(SingerPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private int magicNumber;
    private boolean upgraded;

    public SingerPower(AbstractCreature owner,int amount,int magicNumber,boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.magicNumber=magicNumber;
        this.upgraded=upgraded;
        updateDescription();
    }

    @Override
    public void updateDescription(){
        if(!upgraded){
            description=DESCRIPTIONS[0]+magicNumber+"张牌。";
        }else{
            description=DESCRIPTIONS[0]+magicNumber+"张牌。（ #y已升级 ）";
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof BaseMusicCard){
            applyEffectPower();
        }
    }

//    @Override
//    public void applyFormPower() {
//        addToBot(new ApplyPowerAction(AbstractDungeon.player,
//                AbstractDungeon.player, new SingerPower(AbstractDungeon.player,1), 1));
//    }

    @Override
    public void applyEffectPower() {
        addToBot(new DrawCardAction(AbstractDungeon.player, 1));
    }
}
