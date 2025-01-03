package tomorimod.cards.music.utils;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MusicDamageInfo extends DamageInfo {
    public MusicDamageInfo(AbstractCreature damageSource, int base, DamageInfo.DamageType type) {
        super(damageSource, base, type);
    }
}
