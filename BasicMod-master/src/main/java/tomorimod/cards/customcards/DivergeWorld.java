package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.DivergeWorldPower;
import tomorimod.util.CardStats;

public class DivergeWorld extends BaseCard {
    public static final String ID = makeID(DivergeWorld.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC=1;
    public static final int UPG_MAGIC=0;

    public DivergeWorld() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DivergeWorldPower(p,magicNumber),magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DivergeWorld();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
