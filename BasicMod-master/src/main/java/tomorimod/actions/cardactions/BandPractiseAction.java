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
                BaseMusicCard card= (BaseMusicCard) c.makeStatEquivalentCopy();
                addToTop(new MakeTempCardInHandAction(card));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        for(AbstractCard c:p.hand.group){
            c.isGlowing=true;
        }
        tickDuration();

    }

}
