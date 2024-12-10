package tomorinmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;
import tomorinmod.util.CardStats;

public class NeedAnon extends BaseCard {

    public static final String ID = makeID(NeedAnon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public NeedAnon() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower gravity = AbstractDungeon.player.getPower(Gravity.POWER_ID);

        int gravityAmount = gravity != null ? gravity.amount : 0;
        gravity.amount = gravityAmount*2;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NeedAnon();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}
