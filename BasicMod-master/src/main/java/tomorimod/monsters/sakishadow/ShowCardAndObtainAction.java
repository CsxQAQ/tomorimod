package tomorimod.monsters.sakishadow;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class ShowCardAndObtainAction extends AbstractGameAction {

    private AbstractCard card;
    private float x;
    private float y;


    public ShowCardAndObtainAction(AbstractCard card,float x,float y){
        this.card= card;
        this.x=x;
        this.y=y;
    }


    public void update() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, x,y));
        isDone=true;
    }

}
