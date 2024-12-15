package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.forms.StrengthTomorin;
import tomorinmod.powers.forms.*;

import java.util.HashMap;
import java.util.Map;

public class FormFactory {
    private static final Map<String, FormEffect> formEffectMap = new HashMap<>();

    static {
        if(AbstractDungeon.player!=null){
            AbstractCreature p=AbstractDungeon.player;
            formEffectMap.put("GravityTomorinPower", new GravityTomorinPower(p));
            formEffectMap.put("StrengthTomorinPower", new StrengthTomorinPower(p));
            formEffectMap.put("DarkTomorinPower", new DarkTomorinPower(p));
            formEffectMap.put("ShineTomorinPower", new ShineTomorinPower(p));
            formEffectMap.put("SingerTomorinPower", new SingerTomorinPower(p));
        }
    }

    public static FormEffect getFormEffect(String form) {
        return formEffectMap.getOrDefault(form, null);
    }
}