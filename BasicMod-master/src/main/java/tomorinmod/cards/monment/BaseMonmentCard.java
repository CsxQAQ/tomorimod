package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import java.util.Iterator;

public class BaseMonmentCard extends BaseCard {


    public BaseMonmentCard(String ID, CardStats info) {
        super(ID, info);
    }

    //不加这个autoAdd会报错，不懂为什么，baseMusic不加都可以，可能是因为重写了use？
    public BaseMonmentCard() {
        super("DefaultID", new CardStats(MyCharacter.Meta.CARD_COLOR,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF,
                0));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        Iterator<AbstractCard> iterator = AbstractDungeon.player.masterDeck.group.iterator();

        while (iterator.hasNext()) {
            AbstractCard card = iterator.next();
            if (this.uuid.equals(card.uuid)) {
                iterator.remove();
                break;
            }
        }
    }
}
