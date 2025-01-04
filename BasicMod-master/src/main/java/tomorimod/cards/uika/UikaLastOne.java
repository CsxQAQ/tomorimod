package tomorimod.cards.uika;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.ReversalAction;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public class UikaLastOne extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaLastOne.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public final int MAGIC=20;
    public final int UPG_MAGIC=0;


    public UikaLastOne() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        setBackgroundTexture(imagePath("character/specialcardback/uika_skill.png"),
                imagePath("character/specialcardback/uika_skill_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReversalAction(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaLastOne();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
