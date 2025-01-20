package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import tomorimod.actions.CheckShineGravityAction;
import tomorimod.powers.GravityPower;

import java.util.ArrayList;
import java.util.Arrays;

public class MonsterDamageAllAction extends AbstractGameAction {

    private DamageInfo info;
    private boolean muteSfx;
    private boolean skipWait;
    AbstractMonster source;

    public MonsterDamageAllAction(AbstractMonster source,DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        this.source=source;
        this.muteSfx = false;
        this.skipWait=false;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F) {
            if (this.info.type != DamageInfo.DamageType.THORNS && (this.info.owner.isDying || this.info.owner.halfDead)) {
                this.isDone = true;
                return;
            }


        }

        this.tickDuration();
        if (this.isDone) {
            ArrayList<AbstractCreature> targets=new ArrayList<>(Arrays.asList(AbstractDungeon.player));
            for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!monster.equals(source)&&!monster.isDeadOrEscaped()){
                    targets.add(monster);
                }
            }
            for(AbstractCreature target:targets){
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, this.attackEffect, this.muteSfx));
                if (this.attackEffect == AttackEffect.POISON) {
                    target.tint.color.set(Color.CHARTREUSE.cpy());
                    target.tint.changeColor(Color.WHITE.cpy());
                } else if (this.attackEffect == AttackEffect.FIRE) {
                    target.tint.color.set(Color.RED);
                    target.tint.changeColor(Color.WHITE.cpy());
                }

                if(source instanceof SpecialMonster){
                    //info.applyPowers(source,target);
                    DamageInfo newInfo=new MutsumiDamageInfo(target,info.base);
                    newInfo.applyPowers(source,target);
                    target.damage(new MutsumiDamageInfo(source,newInfo.output,damageType));
                    //target.damage(new DamageInfo(source,info.base,damageType));
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                if (!this.skipWait && !Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(0.1F));
                }
            }
        }
    }
}
