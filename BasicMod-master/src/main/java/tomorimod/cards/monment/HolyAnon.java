package tomorimod.cards.monment;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.character.MyCharacter;
import tomorimod.util.CardStats;

public class HolyAnon extends BaseMonmentCard {
    public static final String ID = makeID(HolyAnon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public HolyAnon() {
        super(ID, info);
        this.exhaust=true;
        setMagic(MAGIC,UPG_MAGIC);
    }

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 5;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new ExpertiseAction(p, 10));

        super.use(p, m);
    }


    @Override
    public AbstractCard makeCopy() {
        return new HolyAnon();
    }


}
