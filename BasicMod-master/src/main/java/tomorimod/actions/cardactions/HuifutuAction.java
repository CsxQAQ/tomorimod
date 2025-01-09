package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.cards.notshow.HuifutuRetainCards;
import tomorimod.cards.notshow.HuifutuRetainEnergy;

import java.util.ArrayList;

public class HuifutuAction extends AbstractGameAction {


    public HuifutuAction(){
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cardGroup=new ArrayList<>();
        cardGroup.add(new HuifutuRetainCards());
        cardGroup.add(new HuifutuRetainEnergy());

        addToBot(new ChooseOneAction(cardGroup));
        isDone = true;
    }

}
