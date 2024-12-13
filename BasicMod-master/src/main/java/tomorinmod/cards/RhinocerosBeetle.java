package tomorinmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class RhinocerosBeetle extends BaseCard {
    public static final String ID = makeID(RhinocerosBeetle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public RhinocerosBeetle() {
        super(ID, info);
        this.selfRetain=true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade(){
        if(!upgraded){
            this.rarity = CardRarity.RARE;
            upgradeName();
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RhinocerosBeetle();
    }

}
