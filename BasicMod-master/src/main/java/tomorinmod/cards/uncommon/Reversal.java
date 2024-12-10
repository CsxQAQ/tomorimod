package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.actions.CheckShineGravityAction;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.rare.MygoTogether;
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

        int gravityAmount = gravity != null ? gravity.amount : 0;
        int shineAmount = shine != null ? shine.amount : 0;

        if(MygoTogether.isMygoTogetherUsed){
            // 直接修改层数
            if (shine != null) {
                shine.amount = gravityAmount;
                shine.updateDescription(); // 更新描述
            }
            if (gravity != null) {
                gravity.amount = shineAmount;
                gravity.updateDescription(); // 更新描述
            }
        }else{
            if (shineAmount > 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, Shine.POWER_ID));
                addToBot(new ApplyPowerAction(p, p, new Gravity(p, shineAmount), shineAmount));
                addToBot(new CheckShineGravityAction(p));

            }
            if (gravityAmount > 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, Gravity.POWER_ID));
                addToBot(new ApplyPowerAction(p, p, new Shine(p, gravityAmount), gravityAmount));
                addToBot(new CheckShineGravityAction(p));
            }
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Reversal();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(0); // 将费用从 1 降为 0
        }
    }
}
