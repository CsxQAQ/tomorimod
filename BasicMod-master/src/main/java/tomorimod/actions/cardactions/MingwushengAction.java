package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.cards.music.utils.MusicDamageInfo;

public class MingwushengAction extends AbstractGameAction {

    private int damage;
    private AbstractMonster target;
    private AbstractCreature source;
    private DamageInfo.DamageType damageType;
    public MingwushengAction(int damage, AbstractCreature source, AbstractMonster target,DamageInfo.DamageType damageType) {
        this.damage=damage;
        this.source=source;
        this.target=target;
        this.damageType=damageType;
    }

    public void update() {
        int gravityAmount=0;
        if (!target.isDying && target.currentHealth > 0 && !target.isEscaping) {
            target.damage(new MusicDamageInfo(this.source, this.damage, this.damageType));
            if (target.lastDamageTaken > 0) {
                gravityAmount=target.lastDamageTaken;
            }
        }
        if(gravityAmount>0){
            AbstractDungeon.actionManager.addToBottom(new ApplyGravityAction(gravityAmount));
        }
        isDone=true;
    }

}
