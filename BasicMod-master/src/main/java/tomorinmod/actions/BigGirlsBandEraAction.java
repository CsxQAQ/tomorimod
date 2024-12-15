package tomorinmod.actions;

import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.music.BaseMusicCard;
import tomorinmod.util.CustomUtils;

import java.util.ArrayList;

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
            if(card instanceof BaseMusicCard){
                ((BaseMusicCard) card).musicRarity= BaseMusicCard.MusicRarity.COMMON;
            }
        }

        addToBot(new ChooseOneAction(cardGroup));
        isDone = true;
    }

    private ArrayList<AbstractCard> getMusicCards() {
        ArrayList<AbstractCard> musicCards = new ArrayList<>();
        for(AbstractCard card: CustomUtils.getAllModCards()){
            if(card instanceof BaseMusicCard){
                musicCards.add(card);
            }
        }
        return musicCards;
    }
}
