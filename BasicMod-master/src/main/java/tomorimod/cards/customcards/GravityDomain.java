package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.notshow.forms.BaseFormCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.GravityDomainPower;
import tomorimod.util.CardStats;

public class GravityDomain extends BaseCard {

    public static final String ID = makeID(GravityDomain.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 2;

    public GravityDomain() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new GravityDomainPower(p,magicNumber),magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new GravityDomain();
    }
}
