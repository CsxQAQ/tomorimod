package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.util.CardStats;

public class UikaLastGentle extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaLastGentle.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public UikaLastGentle() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaLastGentle();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int gravityAmount=uikaMonster.hasPower(makeID("GravityPower"))?uikaMonster.getPower(makeID("GravityPower")).amount:0;
                int shineAmount=uikaMonster.hasPower(makeID("ShinePower"))?uikaMonster.getPower(makeID("ShinePower")).amount:0;
                uikaMonster.getPower(makeID("ShinePower")).amount=gravityAmount;
                uikaMonster.getPower(makeID("GravityPower")).amount=shineAmount;
                isDone=true;
            }
        });
        super.uikaUse(uikaMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
