package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import tomorinmod.cards.music.BaseMusic;
import tomorinmod.cards.special.Band;
import tomorinmod.cards.special.Stone;
import tomorinmod.cards.special.Watermelonworm;
import tomorinmod.util.GetModCardsUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class BigGirlsBandEraAction extends AbstractGameAction {

    private boolean isUpgraded;

    public BigGirlsBandEraAction(boolean isUpgraded){
        this.isUpgraded=isUpgraded;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cardGroup=new ArrayList<>();
        ArrayList<AbstractCard> musicCards = getMusicCards();
        for(int i=0;i<3;i++){
            int randomNumber = AbstractDungeon.miscRng.random(0, musicCards.size()-1);
            AbstractCard selectedCard = musicCards.get(randomNumber);
            cardGroup.add(selectedCard);
            musicCards.remove(randomNumber);
        }
        if (this.isUpgraded){
            for (AbstractCard card : cardGroup){
                card.upgrade();
            }
        }
        for (AbstractCard card : cardGroup){
            card.setCostForTurn(0);
            card.exhaust=true;
            //card.isCostModified = true;
        }
        addToBot(new ChooseOneAction(cardGroup));
        isDone = true;
    }

    private ArrayList<AbstractCard> getMusicCards() {
        ArrayList<AbstractCard> musicCards = new ArrayList<>();
        for(AbstractCard card: GetModCardsUtils.getAllModCards()){
            if(card instanceof BaseMusic){
                musicCards.add(card);
            }
        }
        return musicCards;
    }
}
