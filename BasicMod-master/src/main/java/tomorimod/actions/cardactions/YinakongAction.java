package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.Yinakong;
import tomorimod.cards.music.utils.MusicAttackDamageRandomEnemyAction;
import tomorimod.cards.music.utils.MusicDamageInfo;

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
            addToBot(new MusicAttackDamageRandomEnemyAction(yinakong, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }else {
            addToTop(new DamageAction(target, new MusicDamageInfo(AbstractDungeon.player, damage, yinakong.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        isDone=true;
    }
}
