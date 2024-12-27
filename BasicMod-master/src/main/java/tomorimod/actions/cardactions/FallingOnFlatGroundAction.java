package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.monment.BaseMonmentCard;

public class FallingOnFlatGroundAction extends AbstractGameAction {

    private boolean isUpgraded;

    public FallingOnFlatGroundAction(boolean isUpgraded) {
        this.isUpgraded=isUpgraded;
    }

    private boolean hasOpenedSelectScreen = false; // 标志位，表示是否已打开选择界面

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
//如果不加!hasOpenedSelectScreen判断就会导致剩两张牌时，使用该牌后选择另一张导致h.hand.size()=0，导致后面的逻辑执行不到
        if (p.hand.isEmpty() &&!hasOpenedSelectScreen) {
            isDone = true;
            return;
        }

        if (!hasOpenedSelectScreen) {
            AbstractDungeon.handCardSelectScreen.open("选择一张手牌", 1, false, false);
            hasOpenedSelectScreen = true;
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if(isUpgraded){
                    c.exhaust=true;
                    addToTop(new NewQueueCardAction(c, true,true,true));
                }else{
                    p.hand.moveToExhaustPile(c);
                }
                BaseMonmentCard.removeFromMasterDeck(c);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            isDone = true;
        }
    }
}
