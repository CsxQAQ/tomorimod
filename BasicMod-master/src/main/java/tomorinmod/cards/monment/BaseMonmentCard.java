package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import java.util.Iterator;

public abstract class BaseMonmentCard extends BaseCard {

    public BaseMonmentCard(String ID, CardStats info) {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p.hasPower(makeID("SmallMonmentPower"))){
            removeFromMasterDeck(this);
        }
    }

    public static void removeFromMasterDeck(AbstractCard aCard){
        Iterator<AbstractCard> iterator = AbstractDungeon.player.masterDeck.group.iterator();

        while (iterator.hasNext()) {
            AbstractCard card = iterator.next();
            if (aCard.uuid.equals(card.uuid)) {
                iterator.remove();
                break;
            }
        }
    }
}
