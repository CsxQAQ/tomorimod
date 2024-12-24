package tomorinmod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.forms.BaseFormCard;
import tomorinmod.cards.music.BaseMusicCard;
import tomorinmod.cards.permanentforms.PermanentFrom;
import tomorinmod.powers.forms.FormEffect;

import java.util.ArrayList;

import static tomorinmod.BasicMod.makeID;

public class BandPractiseAction extends AbstractGameAction {

    private ArrayList<AbstractCard> cannotDuplicate=new ArrayList<>();
    private boolean upgraded;
    private boolean hasOpenedSelectScreen=false;

    public BandPractiseAction(boolean upgraded) {
        this.upgraded=upgraded;
    }

    public void update() {
        if (!hasOpenedSelectScreen) {
            AbstractPlayer p=AbstractDungeon.player;
            for(AbstractCard c:p.hand.group){
                if(!(c instanceof BaseMusicCard)){
                    cannotDuplicate.add(c);
                }
            }

            if(cannotDuplicate.size()==p.hand.group.size()){
                isDone=true;
                return;
            }

            p.hand.group.removeAll(this.cannotDuplicate);

            AbstractDungeon.handCardSelectScreen.open("复制一张音乐牌",1,false,false,false,false);
            hasOpenedSelectScreen = true;
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                BaseMusicCard card= (BaseMusicCard) c.makeStatEquivalentCopy();
                if(upgraded){
                    if(card.musicRarity.equals(BaseMusicCard.MusicRarity.COMMON)){
                        card.musicRarity= BaseMusicCard.MusicRarity.UNCOMMON;
                    }else{
                        card.musicRarity= BaseMusicCard.MusicRarity.RARE;
                    }
                }
                card.setDisplayRarity(card.rarity);
                addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
            }

            returnCards();

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotDuplicate) {
            AbstractDungeon.player.hand.addToTop(c);
        }
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}
