package tomorinmod.cards.customcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.cards.music.Chunriying;
import tomorinmod.character.MyCharacter;
import tomorinmod.savedata.customdata.SaveMusicDiscoverd;
import tomorinmod.util.CardStats;
import tomorinmod.util.CustomUtils;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class MemoryInCrychic extends BaseCard {

    public static final String ID = makeID(MemoryInCrychic.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public MemoryInCrychic() {
        super(ID, info);
        exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        BaseMonmentCard card = CustomUtils.monmentCardGroup.get(
                cardRandomRng.random(CustomUtils.monmentCardGroup.size() - 1));
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
