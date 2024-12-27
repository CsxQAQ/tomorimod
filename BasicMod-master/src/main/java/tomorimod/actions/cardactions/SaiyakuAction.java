package tomorimod.actions.cardactions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class SaiyakuAction extends AbstractGameAction {

    ArrayList<AbstractCard> haGroups=null;
    int index;

    public SaiyakuAction(ArrayList<AbstractCard> haGroups,int index) {
        this.haGroups=haGroups;
        this.index=index;
    }

    public void update() {
        AbstractPlayer p=AbstractDungeon.player;
        if(p.hand.group.size() < BaseMod.MAX_HAND_SIZE){
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    p.hand.addToHand(haGroups.get(index));
                    isDone=true;
                }
            });
        }else{
            for(int i=0;i<10;i++){
                haGroups.get(i).baseDamage+=3;
            }
        }
        isDone=true;
    }
}
