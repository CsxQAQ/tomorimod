package tomorimod.monsters.uika.uikacard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.forms.DomainExpansion;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.forms.DomainExpansionPower;
import tomorimod.util.CardStats;

public class UikaDomainExpansion extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaDomainExpansion.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    public final static int MAGIC = 3;
    public final static int UPG_MAGIC = 0;

    public UikaDomainExpansion() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }


    @Override
    public AbstractCard makeCopy() {
        return new UikaDomainExpansion();
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new ApplyPowerAction(uikaMonster,uikaMonster,
                new DomainExpansionPower(uikaMonster, DomainExpansion.MAGIC),DomainExpansion.MAGIC));
        super.uikaUse(uikaMonster);
    }
}
