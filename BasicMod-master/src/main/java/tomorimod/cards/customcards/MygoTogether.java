package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.powers.custompowers.MygoTogetherPower;
import tomorimod.util.CardStats;

public class MygoTogether extends BaseCard {

    public static final String ID = makeID(MygoTogether.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    public final static int MAGIC=1;
    public final static int UPG_MAGIC=1;

    public MygoTogether() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new MygoTogetherPower(p),0));
        addToBot(new ApplyPowerAction(p, p, new GravityPower(p,this.magicNumber),this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ShinePower(p,this.magicNumber),this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MygoTogether();
    }

//    @Override
//    public void upgrade() {
//        if (!upgraded) {
//            upgradeName();
//            upgradeBaseCost(1);
//        }
//    }

}
