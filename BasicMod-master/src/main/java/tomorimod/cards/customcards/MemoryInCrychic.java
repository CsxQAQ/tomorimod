package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;
import tomorimod.util.CustomUtils;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class MemoryInCrychic extends BaseCard {

    public static final String ID = makeID(MemoryInCrychic.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public MemoryInCrychic() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<BaseMonmentCard> tmp=new ArrayList<>(CustomUtils.monmentCardGroup.values());
        BaseMonmentCard card = tmp.get(cardRandomRng.random(tmp.size() - 1));
        if(upgraded){
            card.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MemoryInCrychic();
    }

}
