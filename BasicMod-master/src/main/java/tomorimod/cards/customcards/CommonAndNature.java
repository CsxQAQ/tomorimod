package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.BigGirlsBandEraPower;
import tomorimod.powers.custompowers.BigGirlsBandEraUpgradedPower;
import tomorimod.powers.custompowers.CommonAndNaturePower;
import tomorimod.util.CardStats;

public class CommonAndNature extends BaseCard {

    public static final String ID = makeID(CommonAndNature.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public static final int MAGIC=3;
    public static final int UPG_MAGIC=0;

    public CommonAndNature() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new CommonAndNaturePower(p)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CommonAndNature();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
