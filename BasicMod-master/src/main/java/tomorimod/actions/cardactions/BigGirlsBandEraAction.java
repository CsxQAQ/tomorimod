package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.cards.music.Chunriying;
import tomorimod.patches.CardTipRenderPatch;
import tomorimod.util.CustomUtils;

import java.util.ArrayList;

@Deprecated
public class BigGirlsBandEraAction extends AbstractGameAction {

    private boolean isUpgraded;

    public BigGirlsBandEraAction(boolean isUpgraded){
        this.isUpgraded=isUpgraded;
    }

    @Override
    public void update() {
        ArrayList<BaseMusicCard> musicCardGroup=new ArrayList<>();
        ArrayList<BaseMusicCard> musicCards = new ArrayList<>(CustomUtils.musicCardGroup.values());
        ArrayList<AbstractCard> cardGroup=new ArrayList<>();
        for(int i=0;i<3;i++){
            int randomNumber = AbstractDungeon.miscRng.random(0, musicCards.size()-1);
            BaseMusicCard selectedCard = musicCards.get(randomNumber);
            while(selectedCard instanceof Chunriying){
                randomNumber = AbstractDungeon.miscRng.random(0, musicCards.size()-1);
                selectedCard = musicCards.get(randomNumber);
            }
            musicCardGroup.add(selectedCard);
            musicCards.remove(randomNumber);
        }
        for (BaseMusicCard card : musicCardGroup){
            if(isUpgraded){
                int randomRarity = AbstractDungeon.miscRng.random(2);
                switch (randomRarity){
                    case 0:
                        card.setMusicRarity(BaseMusicCard.MusicRarity.COMMON);
                        break;
                    case 1:
                        card.setMusicRarity(BaseMusicCard.MusicRarity.UNCOMMON);
                        break;
                    case 2:
                        card.setMusicRarity(BaseMusicCard.MusicRarity.RARE);
                        break;
                }
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

}
