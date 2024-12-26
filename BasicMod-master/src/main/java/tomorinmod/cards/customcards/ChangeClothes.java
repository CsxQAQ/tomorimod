package tomorinmod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.custompowers.BigGirlsBandEraPower;
import tomorinmod.powers.custompowers.BigGirlsBandEraUpgradedPower;
import tomorinmod.util.CardStats;
import tomorinmod.util.CustomUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class ChangeClothes extends BaseCard {

    public static final String ID = makeID(ChangeClothes.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            5
    );

    public ChangeClothes() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<BaseFormCard> arr=new ArrayList<>(CustomUtils.formCardGroup.values());
        Collections.shuffle(arr);
        for(BaseFormCard baseFormCard:arr){
            addToBot(new NewQueueCardAction(baseFormCard.makeStatEquivalentCopy(), true, true, true));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChangeClothes();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(4);
        }
    }
}
