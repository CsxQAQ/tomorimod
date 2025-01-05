package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import tomorimod.cards.BaseCard;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.util.CardStats;

import static tomorimod.TomoriMod.imagePath;

public abstract class UikaCard extends BaseCard {
    public UikaCard(String ID, CardStats info) {
        super(ID, info);
        switch (info.cardType){
            default:
            case SKILL:
                setBackgroundTexture(imagePath("character/specialcardback/uika_skill.png"),
                        imagePath("character/specialcardback/uika_skill_p.png"));
                break;
            case ATTACK:
                setBackgroundTexture(imagePath("character/specialcardback/uika_attack.png"),
                        imagePath("character/specialcardback/uika_attack_p.png"));
                break;
            case POWER:
                setBackgroundTexture(imagePath("character/specialcardback/uika_power.png"),
                        imagePath("character/specialcardback/uika_power_p.png"));
                break;
        }

    }

    public void uikaUse(UikaMonster uikaMonster){
        uikaMonster.showCardsDiscard(this);
        addToBot(new TrueWaitAction(UikaMonster.WAIT_TIME));
    }

}
