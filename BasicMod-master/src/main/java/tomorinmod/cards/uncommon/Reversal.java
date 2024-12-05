package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;
import tomorinmod.util.CardStats;

public class Reversal extends BaseCard {
    public static final String ID = makeID(Reversal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Reversal() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower gravity = AbstractDungeon.player.getPower(Gravity.POWER_ID);
        AbstractPower shine = AbstractDungeon.player.getPower(Shine.POWER_ID);

        int gravityAmount = gravity != null ? gravity.amount : 0;  // 如果有重力能力，获取其层数
        int shineAmount = shine != null ? shine.amount : 0;        // 如果有闪耀能力，获取其层数

        // 将闪耀层数转换为重力层数
        if (shineAmount > 0) {
            addToBot(new RemoveSpecificPowerAction(p, p, Shine.POWER_ID) {
                @Override
                public void update() {
                    super.update();
                    if (isDone) {  // 确保移除闪耀能力已经完成
                        addToBot(new ApplyPowerAction(p, p, new Gravity(p, shineAmount), shineAmount));
                    }
                }
            });
        }
        else if (gravityAmount > 0) {
            addToBot(new RemoveSpecificPowerAction(p, p, Gravity.POWER_ID) {
                @Override
                public void update() {
                    super.update();
                    if (isDone) {
                        addToBot(new ApplyPowerAction(p, p, new Shine(p, gravityAmount), gravityAmount));
                    }
                }
            });
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Reversal();
    }
}
