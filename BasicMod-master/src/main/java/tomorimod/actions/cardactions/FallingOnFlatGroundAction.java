package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.tags.CustomTags;

public class FallingOnFlatGroundAction extends AbstractGameAction {

    private boolean isUpgraded;
    private AbstractPlayer p;

    public FallingOnFlatGroundAction(boolean isUpgraded) {
        this.isUpgraded=isUpgraded;
        this.p=AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;

    }

    private boolean hasOpenedSelectScreen = false; // 标志位，表示是否已打开选择界面

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.hand.group) {
                c.isGlowing=false;
                tmp.addToRandomSpot(c);
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
                if(isUpgraded){
                    c.exhaust=true;
                    addToTop(new NewQueueCardAction(c, true,true,true));
                }else{
                    p.hand.moveToExhaustPile(c);
                }
                BaseMonmentCard.removeFromMasterDeck(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        for(AbstractCard c:p.hand.group){
            c.isGlowing=true;
        }
        tickDuration();


//        AbstractPlayer p = AbstractDungeon.player;
////如果不加!hasOpenedSelectScreen判断就会导致剩两张牌时，使用该牌后选择另一张导致h.hand.size()=0，导致后面的逻辑执行不到
//        if (p.hand.isEmpty() &&!hasOpenedSelectScreen) {
//            isDone = true;
//            return;
//        }
//
//        if (!hasOpenedSelectScreen) {
//            AbstractDungeon.handCardSelectScreen.open("选择一张手牌", 1, false, false);
//            hasOpenedSelectScreen = true;
//            return;
//        }
//
//        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
//            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
//                if(isUpgraded){
//                    c.exhaust=true;
//                    addToTop(new NewQueueCardAction(c, true,true,true));
//                }else{
//                    p.hand.moveToExhaustPile(c);
//                }
//                BaseMonmentCard.removeFromMasterDeck(c);
//            }
//            CardCrawlGame.dungeon.checkForPactAchievement();
//            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
//            isDone = true;
//        }
    }
}
