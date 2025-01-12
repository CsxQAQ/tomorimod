package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.cards.notshow.utilcards.TestCard;
import tomorimod.tags.CustomTags;
import tomorimod.util.MonsterUtils;

import java.util.ArrayList;

public class WulushiAction extends AbstractGameAction {

    private int baseDamage;
    private AbstractMonster except;
    private AbstractCard card;

    public WulushiAction(AbstractCard card, int baseDamage, AbstractMonster except) {
        this.baseDamage=baseDamage;
        this.except=except;
        this.card=card;
    }

    public void update() {
        if(this.baseDamage<=0){
            isDone=true;
            return;
        }

        AbstractMonster target= MonsterUtils.getRandomEnemy(except);
        if(target==null){
            isDone=true;
            return;
        }
        card.baseDamage=this.baseDamage;

        card.calculateCardDamage(target);

        addToTop(new WulushiAction(card,baseDamage-1,target));
        addToTop(new DamageAction(target, new MusicDamageInfo(AbstractDungeon.player,
                card.damage, card.damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
        isDone=true;
    }


}
