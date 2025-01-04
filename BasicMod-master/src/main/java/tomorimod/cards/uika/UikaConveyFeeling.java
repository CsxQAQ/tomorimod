package tomorimod.cards.uika;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.ConveyFeelingPower;
import tomorimod.util.CardStats;

import javax.smartcardio.Card;

public class UikaConveyFeeling extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaConveyFeeling.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public static int maxHPOverflow=0;

    public UikaConveyFeeling() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ConveyFeelingPower(p),0));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new UikaConveyFeeling();
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
