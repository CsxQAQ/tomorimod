package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.forms.BaseFormCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;
import tomorimod.util.CustomUtils;

import java.util.ArrayList;
import java.util.Collections;

public class ChangeClothes extends BaseCard {

    public static final String ID = makeID(ChangeClothes.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
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
