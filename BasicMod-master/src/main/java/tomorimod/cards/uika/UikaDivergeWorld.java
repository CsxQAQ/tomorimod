package tomorimod.cards.uika;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.DivergeWorldPower;
import tomorimod.util.CardStats;

public class UikaDivergeWorld extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaDivergeWorld.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public UikaDivergeWorld() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DivergeWorldPower(p,1),1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaDivergeWorld();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}
