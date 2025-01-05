package tomorimod.actions.cardactions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.music.utils.MusicDamageInfo;
import tomorimod.cards.notshow.TestCard;
import tomorimod.tags.CustomTags;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class WulushiAction extends AbstractGameAction {

    private int baseDamage;
    private TestCard testCard=new TestCard();
    private AbstractMonster except;

    public WulushiAction(int baseDamage, AbstractMonster except) {
        this.baseDamage=baseDamage;
        this.except=except;
    }

    public void update() {
        if(baseDamage<=0){
            isDone=true;
            return;
        }

        AbstractMonster target=getRandomEnemy(except);
        if(target==null){
            isDone=true;
            return;
        }
        testCard.baseDamage=this.baseDamage;

        testCard.calculateCardDamage(target);

        addToTop(new WulushiAction(baseDamage-1,target));
        addToTop(new DamageAction(target, new MusicDamageInfo(AbstractDungeon.player,
                testCard.damage, CustomTags.MUSIC), AttackEffect.SLASH_DIAGONAL));


        isDone=true;
    }

    private AbstractMonster getRandomEnemy(AbstractMonster except) {
        ArrayList<AbstractMonster> possibleTargets = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (monster!=except&&!monster.isDying && !monster.isDeadOrEscaped()&&!monster.hasPower(makeID("FriendlyMonsterPower"))) {
                possibleTargets.add(monster);
            }
        }
        if (possibleTargets.isEmpty()) {
            return null;
        }
        return possibleTargets.get(AbstractDungeon.miscRng.random(possibleTargets.size() - 1));
    }
}
