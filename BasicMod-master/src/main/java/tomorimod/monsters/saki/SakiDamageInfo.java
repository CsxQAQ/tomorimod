package tomorimod.monsters.saki;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.monsters.mutsumi.MutsumiMonster;

import java.util.Iterator;

public class SakiDamageInfo extends DamageInfo {

    public SakiDamageInfo(AbstractCreature damageSource, int base, DamageType type) {
        super(damageSource, base, type);
    }

    public SakiDamageInfo(AbstractCreature owner, int base) {
        this(owner, base, DamageType.NORMAL);
    }

}
