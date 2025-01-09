package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.customcards.MygoTogether;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;
import tomorimod.powers.custompowers.MygoTogetherPower;
import tomorimod.util.CardStats;

public class UikaMygoTogether extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaMygoTogether.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );


    public final static int MAGIC=1;
    public final static int UPG_MAGIC=1;

    public UikaMygoTogether() {
        super(ID, info);
        this.setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaMygoTogether();
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new ApplyPowerAction(uikaMonster,uikaMonster,new MygoTogetherPower(uikaMonster)));
        addToBot(new ApplyPowerAction(uikaMonster,uikaMonster,
                new GravityPower(uikaMonster, MygoTogether.MAGIC),MygoTogether.MAGIC));
        addToBot(new ApplyPowerAction(uikaMonster,uikaMonster,
                new ShinePower(uikaMonster,MygoTogether.MAGIC),MygoTogether.MAGIC));
        super.uikaUse(uikaMonster);
    }
}
