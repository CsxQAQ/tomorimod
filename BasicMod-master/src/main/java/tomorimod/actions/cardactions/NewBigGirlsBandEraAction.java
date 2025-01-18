package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.cards.music.Chunriying;
import tomorimod.util.CustomUtils;

import java.util.ArrayList;

public class NewBigGirlsBandEraAction extends AbstractGameAction {
    public static int numPlaced;
    private boolean retrieveCard = false;
    private boolean isUpgraded;

    public NewBigGirlsBandEraAction(boolean isUpgraded) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.isUpgraded=isUpgraded;
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard codexCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                codexCard.current_x = -1000.0F * Settings.xScale;
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(codexCard));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> generateCardChoices() {
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
        return cardGroup;
    }
}
