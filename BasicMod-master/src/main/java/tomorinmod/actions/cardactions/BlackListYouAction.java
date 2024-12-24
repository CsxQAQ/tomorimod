package tomorinmod.actions.cardactions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import tomorinmod.powers.GravityPower;

public class BlackListYouAction extends AbstractGameAction {

    private AbstractMonster m;

    public BlackListYouAction(AbstractMonster m) {
        this.m=m;
    }

    public void update() {
        if (m != null) {
            if (m.type != AbstractMonster.EnemyType.BOSS&&m.currentHealth>0) {
                addToTop(new InstantKillAction(m));
                addToTop(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY, Color.GOLD.cpy())));
            } else {
                addToTop(new TextAboveCreatureAction(m, "无效！"));
            }
        }
        isDone=true;
    }

}
