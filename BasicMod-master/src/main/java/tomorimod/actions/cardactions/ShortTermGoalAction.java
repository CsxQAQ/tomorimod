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
import tomorimod.tags.CustomTags;

public class ShortTermGoalAction extends AbstractGameAction {

    private AbstractPlayer p;

    public ShortTermGoalAction() {
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
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, 1, false, "选择1张手牌");
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.tags.add(CustomTags.SHORTTERMGOAL);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        for(AbstractCard c:AbstractDungeon.player.hand.group){
            c.isGlowing=true;
        }

        tickDuration();
    }
}