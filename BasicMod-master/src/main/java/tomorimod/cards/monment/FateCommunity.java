package tomorimod.cards.monment;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.MultiSmoothHealthChangeAction;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;
import tomorimod.vfx.FateCommunityEffect;

import java.util.ArrayList;
import java.util.List;

public class FateCommunity extends BaseMonmentCard {
    public static final String ID = makeID(FateCommunity.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ALL,
            1
    );

    public FateCommunity() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;

                double totalPercentage = 0.0;
                double playerPercentage = (double) p.currentHealth / p.maxHealth;
                totalPercentage += playerPercentage;

                int activeMonsterCount = 0;
                for (AbstractMonster monster : monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        double monsterPercentage = (double) monster.currentHealth / monster.maxHealth;
                        totalPercentage += monsterPercentage;
                        activeMonsterCount++;
                    }
                }

                int totalUnits = activeMonsterCount + 1;
                double averagePercentage = totalPercentage / totalUnits;

                // 准备好所有目标
                List<AbstractCreature> allTargets = new ArrayList<>();
                List<Integer> startHealths = new ArrayList<>();
                List<Integer> endHealths   = new ArrayList<>();

                // 先加玩家
                allTargets.add(p);
                startHealths.add(p.currentHealth);
                int newPlayerHealth = (int) Math.ceil(p.maxHealth * averagePercentage);
                endHealths.add(Math.max(1, newPlayerHealth));

                // 再加存活怪物
                for (AbstractMonster monster : monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        allTargets.add(monster);
                        startHealths.add(monster.currentHealth);
                        int newMonsterHealth = (int) Math.ceil(monster.maxHealth * averagePercentage);
                        endHealths.add(Math.max(1, newMonsterHealth));
                    }
                }

                // 调用 多单位平滑改血 Action
                addToBot(new MultiSmoothHealthChangeAction(allTargets, startHealths, endHealths));

                // 你要显示的效果也可以一次性加入
                for (AbstractCreature c : allTargets) {
                    AbstractDungeon.effectsQueue.add(new FateCommunityEffect(c));
                }

                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FateCommunity();
    }


}
