package tomorimod.cards.uika;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class UikaDomainExpansion extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaDomainExpansion.class.getSimpleName());
    private static final CardStats info = new CardStats(
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
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
