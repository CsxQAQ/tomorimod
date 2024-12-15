package tomorinmod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;
import tomorinmod.util.CardStats;

public class MygoTogether extends BaseCard {

    public static final String ID = makeID(MygoTogether.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public static boolean isMygoTogetherUsed=false;

    public final static int MAGIC=1;
    public final static int UPG_MAGIC=1;

    public MygoTogether() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        isMygoTogetherUsed=true;
        addToBot(new ApplyPowerAction(p, p, new Gravity(p,this.magicNumber),this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new Shine(p,this.magicNumber),this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MygoTogether();
    }

}
