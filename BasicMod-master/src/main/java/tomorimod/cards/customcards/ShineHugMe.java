package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.ShinePower;
import tomorimod.util.CardStats;
import tomorimod.util.PlayerUtils;

public class ShineHugMe extends BaseCard {
    public static final String ID = makeID(ShineHugMe.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public static final int MAGIC=1;
    public static final int UPG_MAGIC=1;

    public ShineHugMe() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber*
                PlayerUtils.getPowerNum( makeID("ShinePower"))));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShineHugMe();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
