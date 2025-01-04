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

public class UikaSumimi extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaSumimi.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC=2;
    public final static int UPG_MAGIC=0;


    public UikaSumimi() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
        setBackgroundTexture(imagePath("character/specialcardback/uika_attack.png"),
                imagePath("character/specialcardback/uika_attack_p.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReversalAction(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaSumimi();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
