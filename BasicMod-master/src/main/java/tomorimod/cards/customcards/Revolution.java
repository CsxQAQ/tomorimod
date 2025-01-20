package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.util.CardStats;
import tomorimod.util.PlayerUtils;

public class Revolution extends BaseCard {
    public static final String ID = makeID(Revolution.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Revolution() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int gravityAmount= PlayerUtils.getPowerNum(makeID("GravityPower"));
        addToBot(new RemoveSpecificPowerAction(p,p,makeID("GravityPower")));
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,gravityAmount),gravityAmount));
    }

    @Override
    public void upgrade(){
        if(!upgraded){
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Revolution();
    }

}
