package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.WeAreMygoPower;
import tomorinmod.savedata.customdata.SavePermanentForm;
import tomorinmod.util.CardStats;

public class WeAreMygo extends BaseMonmentCard {

    public static final String ID = makeID(WeAreMygo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public WeAreMygo() {
        super(ID, info);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WeAreMygoPower(p),1));

        //SavePermanentForm.getInstance().getForms().add("WeAreMygoPower");
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new WeAreMygo();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(0); // 将费用从 1 降为 0
        }
    }
}
