package tomorimod.monsters.uika.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.actions.ApplyShineAction;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class UikaPoemInsteadOfSong extends UikaCard implements WithoutMaterial {
    public static final String ID = makeID(UikaPoemInsteadOfSong.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public UikaPoemInsteadOfSong() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int shineNum = 0;

                for (AbstractPower power : p.powers) {
                    if (power.type == AbstractPower.PowerType.DEBUFF) {
                        shineNum += power.amount;
                        addToBot(new RemoveSpecificPowerAction(p,p,power.ID));
                    }
                }

                if (shineNum > 0) {
                    addToBot(new ApplyShineAction(shineNum));
                }
                isDone=true;
            }
        });

    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaPoemInsteadOfSong();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}
