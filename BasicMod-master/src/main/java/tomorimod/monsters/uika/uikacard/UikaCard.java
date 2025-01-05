package tomorimod.monsters.uika.uikacard;

import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import tomorimod.cards.BaseCard;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.util.CardStats;

public abstract class UikaCard extends BaseCard {
    public UikaCard(String ID, CardStats info) {
        super(ID, info);
    }
    public int position;

    public void uikaUse(UikaMonster uikaMonster){
        uikaMonster.showCardsDiscard(this);
        addToBot(new TrueWaitAction(UikaMonster.WAIT_TIME));
    }

}
