package tomorinmod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.music.Yinakong;

public class YinakongAction extends AbstractGameAction {


    private int damage;
    private AbstractMonster target = null;
    private Yinakong yinakong;

    public YinakongAction(int damage, AbstractMonster target,Yinakong yinakong) {
        this.damage = damage;
        this.target = target;
        this.yinakong=yinakong;
    }

    @Override
    public void update() {
        if (target.isDying||target.currentHealth <= 0) {
            addToBot(new AttackDamageRandomEnemyAction(yinakong, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }else {
            addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        isDone=true;
    }
}
