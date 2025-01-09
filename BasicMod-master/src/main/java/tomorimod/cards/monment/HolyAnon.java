package tomorimod.cards.monment;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class HolyAnon extends BaseMonmentCard {
    public static final String ID = makeID(HolyAnon.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public HolyAnon() {
        super(ID, info);
        this.exhaust=true;
        setMagic(MAGIC,UPG_MAGIC);
    }

    public static final int MAGIC = 5;
    public static final int UPG_MAGIC = 5;

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
