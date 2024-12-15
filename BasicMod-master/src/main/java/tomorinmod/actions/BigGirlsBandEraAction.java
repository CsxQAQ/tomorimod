package tomorinmod.actions;

import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.music.BaseMusicCard;
import tomorinmod.util.CustomUtils;

import java.util.ArrayList;

import static tomorinmod.BasicMod.makeID;

public class BigGirlsBandEraAction extends AbstractGameAction {

    private boolean isUpgraded;

    public BigGirlsBandEraAction(boolean isUpgraded){
        this.isUpgraded=isUpgraded;
    }

    @Override
    public void update() {
        ArrayList<BaseMusicCard> musicCardGroup=new ArrayList<>();
        ArrayList<BaseMusicCard> musicCards = new ArrayList<>(getMusicCards());
        ArrayList<AbstractCard> cardGroup=new ArrayList<>();
        for(int i=0;i<3;i++){
            int randomNumber = AbstractDungeon.miscRng.random(0, musicCards.size()-1);
            BaseMusicCard selectedCard = musicCards.get(randomNumber);
            musicCardGroup.add(selectedCard);
            musicCards.remove(randomNumber);
        }
        for (BaseMusicCard card : musicCardGroup){
            if(isUpgraded){
                int randomRarity = AbstractDungeon.miscRng.random(2);
                switch (randomRarity){
                    case 0:
                        card.setRarity(BaseMusicCard.MusicRarity.COMMON);
                        break;
                    case 1:
                        card.setRarity(BaseMusicCard.MusicRarity.UNCOMMON);
                        break;
                    case 2:
                        card.setRarity(BaseMusicCard.MusicRarity.RARE);
                        break;
                }
                card.setBanner();
            }else{
                card.musicRarity= BaseMusicCard.MusicRarity.COMMON;
            }
            card.setCostForTurn(0);
            card.exhaust=true;

            AbstractCard cloneCard=card.makeStatEquivalentCopy();
            cardGroup.add(cloneCard);
        }

        addToBot(new ChooseOneAction(cardGroup));
        isDone = true;
    }

    private ArrayList<BaseMusicCard> getMusicCards() {
        ArrayList<BaseMusicCard> musicCards = new ArrayList<>();
        for(AbstractCard card: CustomUtils.getAllModCards()){
            if(card instanceof BaseMusicCard&&!card.cardID.equals(makeID("Chunriying"))){ //排除春日影
                BaseMusicCard baseMusicCard=(BaseMusicCard) card;
                musicCards.add(baseMusicCard);
            }
        }
        return musicCards;
    }
}
