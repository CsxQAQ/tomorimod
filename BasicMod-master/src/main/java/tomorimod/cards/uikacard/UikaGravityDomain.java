package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.customcards.GravityDomain;
import tomorimod.cards.notshow.forms.GravityDomainForm;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.custompowers.GravityDomainPower;
import tomorimod.powers.forms.GravityDomainFormPower;
import tomorimod.util.CardStats;

public class UikaGravityDomain extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaGravityDomain.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 0;

    public UikaGravityDomain() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }


    @Override
    public AbstractCard makeCopy() {
        return new UikaGravityDomain();
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new ApplyPowerAction(uikaMonster,uikaMonster,
                new GravityDomainPower(uikaMonster, GravityDomain.MAGIC), GravityDomain.MAGIC));
        super.uikaUse(uikaMonster);
    }
}
