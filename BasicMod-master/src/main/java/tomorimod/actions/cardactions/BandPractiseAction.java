package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.cards.music.BaseMusicCard;

import java.util.ArrayList;

public class BandPractiseAction extends AbstractGameAction {

    private ArrayList<AbstractCard> cannotDuplicate=new ArrayList<>();
    private boolean upgraded;
    private boolean hasOpenedSelectScreen=false;
    private AbstractPlayer p;

    public BandPractiseAction() {
        this.upgraded=upgraded;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.hand.group) {
                if(c instanceof BaseMusicCard){
                    c.isGlowing=false;
                    tmp.addToRandomSpot(c);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, 1, false, "选择1张手牌");
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                //addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                BaseMusicCard card= (BaseMusicCard) c.makeStatEquivalentCopy();
//                if(upgraded){
//                    if(card.musicRarity.equals(BaseMusicCard.MusicRarity.COMMON)){
//                        card.musicRarity= BaseMusicCard.MusicRarity.UNCOMMON;
//                    }else{
//                        card.musicRarity= BaseMusicCard.MusicRarity.RARE;
//                    }
//                }
//                card.setDisplayRarity(card.rarity);
                addToTop(new MakeTempCardInHandAction(card));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        for(AbstractCard c:p.hand.group){
            c.isGlowing=true;
        }
        tickDuration();
//        if (!hasOpenedSelectScreen) {
//            AbstractPlayer p=AbstractDungeon.player;
//            for(AbstractCard c:p.hand.group){
//                if(!(c instanceof BaseMusicCard)){
//                    cannotDuplicate.add(c);
//                }
//            }
//
//            if(cannotDuplicate.size()==p.hand.group.size()){
//                isDone=true;
//                return;
//            }
//
//            p.hand.group.removeAll(this.cannotDuplicate);
//
//            AbstractDungeon.handCardSelectScreen.open("复制一张音乐牌",1,false,false,false,false);
//            hasOpenedSelectScreen = true;
//            return;
//        }
//
//        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
//            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
//                addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
//                BaseMusicCard card= (BaseMusicCard) c.makeStatEquivalentCopy();
//                if(upgraded){
//                    if(card.musicRarity.equals(BaseMusicCard.MusicRarity.COMMON)){
//                        card.musicRarity= BaseMusicCard.MusicRarity.UNCOMMON;
//                    }else{
//                        card.musicRarity= BaseMusicCard.MusicRarity.RARE;
//                    }
//                }
//                card.setDisplayRarity(card.rarity);
//                addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
//            }
//
//            returnCards();
//
//            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
//            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
//            this.isDone = true;
//        }
    }

//    private void returnCards() {
//        for (AbstractCard c : this.cannotDuplicate) {
//            AbstractDungeon.player.hand.addToTop(c);
//        }
//        AbstractDungeon.player.hand.refreshHandLayout();
//    }
}
