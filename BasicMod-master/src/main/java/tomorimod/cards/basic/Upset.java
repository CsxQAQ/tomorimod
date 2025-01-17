package tomorimod.cards.basic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class Upset extends BaseCard {

    public static final String ID = makeID(Upset.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    public final int BLOCK=7;
    public final int UPG_BLOCK=4;

    public final int MAGIC=3;
    public final int UPG_MAGIC=2;

    public Upset() {
        super(ID, info);
        setBlock(BLOCK,UPG_BLOCK);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyGravityAction(magicNumber));
        addToBot(new GainBlockAction(p,block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Upset();
    }
}
