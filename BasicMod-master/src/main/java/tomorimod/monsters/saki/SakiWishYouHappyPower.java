package tomorimod.monsters.saki;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import tomorimod.powers.BasePower;

import java.awt.*;

import static tomorimod.TomoriMod.makeID;

public class SakiWishYouHappyPower extends BasePower {
    public static final String POWER_ID = makeID(SakiWishYouHappyPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SakiWishYouHappyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        addToBot(new DamageAction(AbstractDungeon.player,new SakiDamageInfo(this.owner,this.amount),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            for(int i=0;i< EnergyPanel.totalCount;i++){
                addToBot(new DamageAction(AbstractDungeon.player,new SakiDamageInfo(this.owner,this.amount),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
    }

    @Override
    public void updateDescription() {
        description=DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]+amount + DESCRIPTIONS[2];
    }

}
