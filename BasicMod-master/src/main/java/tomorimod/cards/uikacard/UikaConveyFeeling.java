package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.basic.Defend;
import tomorimod.cards.customcards.ConveyFeeling;
import tomorimod.character.Tomori;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.powers.custompowers.ConveyFeelingPower;
import tomorimod.util.CardStats;

public class UikaConveyFeeling extends UikaCard implements WithoutMaterial {

    public static final String ID = makeID(UikaConveyFeeling.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    public UikaConveyFeeling() {
        super(ID, info);
    }

    @Override
    public void uikaUse(UikaMonster uikaMonster) {
        addToBot(new ApplyPowerAction(uikaMonster,uikaMonster,new ConveyFeelingPower(uikaMonster)));
        super.uikaUse(uikaMonster);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new UikaConveyFeeling();
    }




}
