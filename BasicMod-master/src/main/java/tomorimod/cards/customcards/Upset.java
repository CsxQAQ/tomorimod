package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.MyCharacter;
import tomorimod.util.CardStats;

public class Upset extends BaseCard {

    public static final String ID = makeID(Upset.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private final int BLOCK=2;
    private final int UPG_BLOCK=1;

    public Upset() {
        super(ID, info);
        setBlock(BLOCK,UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyGravityAction(block));
        addToBot(new GainBlockAction(p,block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Upset();
    }
}
