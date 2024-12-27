package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.MyCharacter;
import tomorimod.powers.GravityPower;
import tomorimod.util.CardStats;

public class GravitySurge extends BaseCard {
    public static final String ID = makeID(GravitySurge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public GravitySurge() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyGravityAction(magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPower gravity = p.getPower(GravityPower.POWER_ID);

                if (gravity != null) {
                    for (int i = 0; i < gravity.amount; i++) {
                        addToBot(new DrawCardAction(p, 1));
                    }
                }
                isDone=true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new GravitySurge();
    }
}