package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class Green extends BaseCard {
    public static final String ID = makeID(Green.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public static final int BLOCK = 5;
    public static final int UPG_BLOCK = 3;


    public Green() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        //CustomUtils.setRareBanner(this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Green();
    }


}
