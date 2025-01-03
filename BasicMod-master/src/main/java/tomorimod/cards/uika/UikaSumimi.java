package tomorimod.cards.uika;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.ReversalAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;
import static tomorimod.TomoriMod.imagePath;

public class UikaSumimi extends BaseCard implements UikaCard {
    public static final String ID = makeID(UikaSumimi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public final static int MAGIC=2;
    public final static int UPG_MAGIC=0;


    @Override
    public void update() {
        super.update();
        this.setMagic(MAGIC,UPG_MAGIC);
    }
    public UikaSumimi() {
        super(ID, info);
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
