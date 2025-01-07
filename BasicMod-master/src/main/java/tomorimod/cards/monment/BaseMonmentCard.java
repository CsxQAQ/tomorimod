package tomorimod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.util.CardStats;
import tomorimod.vfx.ExhaustMasterDeckEffect;
import tomorimod.vfx.MonmentEffect;

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
                AbstractDungeon.effectsQueue.add(new MonmentEffect(card,
                        Settings.WIDTH -200.0f*Settings.scale, Settings.HEIGHT -200.0f*Settings.scale));
                iterator.remove();
                //addExhaustEffectOnMasterDeck();
                break;
            }
        }
    }

//    public static void addExhaustEffectOnMasterDeck(){
//        AbstractDungeon.effectsQueue.add(new ExhaustMasterDeckEffect(
//                Settings.WIDTH -100.0f*Settings.scale, Settings.HEIGHT -100.0f*Settings.scale));
//    }

    @Override
    public BaseMonmentCard makeStatEquivalentCopy() {
        AbstractCard BaseCard = super.makeStatEquivalentCopy();
        return (BaseMonmentCard)BaseCard;
    }
}
