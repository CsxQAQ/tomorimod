package tomorinmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class SmoothCombo extends BaseCard {
    public static final String ID = makeID(SmoothCombo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            3
    );

    public static int smoothComboUseTime=0;
    public static CardType recordedType = null; // 记录第一张打出卡的类型
    public SmoothCombo() {
        super(ID, info);
    }


    public static void reset(){
        smoothComboUseTime = 0;
        recordedType=null;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        smoothComboUseTime=1;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SmoothCombo();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(2);
        }
    }
}
