package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.monitors.HandleFormsMonitor;
import tomorinmod.powers.forms.DarkTomorinPower;
import tomorinmod.relics.MicrophoneRelic;
import tomorinmod.util.CardStats;

public class DarkTomorin extends BaseFormCard {

    public static final String ID = makeID(DarkTomorin.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public DarkTomorin() {
        super(ID, info);
        setFormPower();
        exhaust=true;
    }

    @Override
    public void setFormPower(){
        formPower="DarkTomorinPower";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        addToBot(new ApplyPowerAction(p, p, new DarkTomorinPower(p),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkTomorin();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
