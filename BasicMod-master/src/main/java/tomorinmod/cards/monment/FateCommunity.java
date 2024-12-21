package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.SmoothHealthChangeAction;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;
import tomorinmod.vfx.FateCommunityEffect;

import java.util.ArrayList;

public class FateCommunity extends BaseMonmentCard {
    public static final String ID = makeID(FateCommunity.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ALL,
            3
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

                int newPlayerHealth = (int) Math.ceil(p.maxHealth * averagePercentage);
                addToBot(new SmoothHealthChangeAction(p, p.currentHealth, Math.max(1, newPlayerHealth)));

                AbstractDungeon.effectsQueue.add(new FateCommunityEffect(p));

                for (AbstractMonster monster : monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        int newMonsterHealth = (int) Math.ceil(monster.maxHealth * averagePercentage);
                        addToBot(new SmoothHealthChangeAction(monster, monster.currentHealth, Math.max(1, newMonsterHealth)));
                        AbstractDungeon.effectsQueue.add(new FateCommunityEffect(monster));
                    }
                }
                isDone=true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FateCommunity();
    }


}
