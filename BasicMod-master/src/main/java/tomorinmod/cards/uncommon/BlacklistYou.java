package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.CustomUtils;
import tomorinmod.util.CardStats;

public class BlacklistYou extends BaseCard {
    public static final String ID = makeID(BlacklistYou.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );


    public BlacklistYou() {
        super(ID, info);
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            if (m.type != AbstractMonster.EnemyType.BOSS) {
                // 设置生命值为 0，直接消灭
                m.currentHealth = 0;
                m.die(); // 调用死亡方法，处理后续逻辑
            } else {
                // 如果是 Boss，显示效果无效的提示
                AbstractDungeon.actionManager.addToBottom(
                        new com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction(
                                m, "无效！"
                        )
                );
            }
            CustomUtils.addTags(this, CustomTags.MOMENT);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BlacklistYou();
    }


}
