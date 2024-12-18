package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.monitors.HandleFormsMonitor;
import tomorinmod.powers.forms.StrengthTomorinPower;
import tomorinmod.util.CardStats;

public class StrengthTomorin extends BaseFormCard {

    public static final String ID = makeID(StrengthTomorin.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public StrengthTomorin() {
        super(ID, info);
        setFormPower();
    }

    @Override
    public void setFormPower(){
        formPower="StrengthTomorinPower";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        super.use(p,m);
        addToBot(new ApplyPowerAction(p, p, new StrengthTomorinPower(p),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrengthTomorin();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
