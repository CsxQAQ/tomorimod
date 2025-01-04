package tomorimod.cards.uika;

import tomorimod.cards.BaseCard;
import tomorimod.util.CardStats;

public abstract class UikaCard extends BaseCard {
    public UikaCard(String ID, CardStats info) {
        super(ID, info);
    }
    public int position;
}
