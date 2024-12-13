package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class InterestingGirl extends BaseMonmentCard {

    public static final String ID = makeID(InterestingGirl.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            -1
    );

    public InterestingGirl() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int energyNum=this.energyOnUse;
        addToBot(new ApplyPowerAction(
                p, p, new IntangiblePlayerPower(p, energyNum+1), energyNum+1));
        p.energy.use(energyNum);
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new InterestingGirl();
    }
}
