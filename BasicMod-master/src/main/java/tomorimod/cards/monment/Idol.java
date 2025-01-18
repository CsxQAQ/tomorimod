package tomorimod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.ApplyShineAction;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

public class Idol extends BaseMonmentCard {
    public static final String ID = makeID(Idol.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Idol() {
        super(ID, info);
        this.exhaust=true;
        setMagic(MAGIC,UPG_MAGIC);
    }

    public static final int MAGIC = 10;
    public static final int UPG_MAGIC = 10;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyShineAction(magicNumber));

        super.use(p, m);
    }


    @Override
    public AbstractCard makeCopy() {
        return new Idol();
    }


}
