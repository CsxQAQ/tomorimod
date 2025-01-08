package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class FailAndRestartAction
        extends AbstractGameAction {

    private AbstractPlayer p;

    public FailAndRestartAction() {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.hand.group) {
                c.isGlowing=false;
                tmp.addToRandomSpot(c);
            }

            if (tmp.size() == 0) {
                this.isDone = true; return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, 99, true, "选择X张手牌消耗");
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.p.hand.moveToExhaustPile(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            //this.p.hand.refreshHandLayout();
        }
        for(AbstractCard c:AbstractDungeon.player.hand.group){
            c.selfRetain=true;
            c.isGlowing=true;
        }

        tickDuration();
    }
}