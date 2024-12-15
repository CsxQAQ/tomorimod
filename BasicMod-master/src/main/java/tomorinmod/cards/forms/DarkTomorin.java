package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.forms.DarkTomorinPower;
import tomorinmod.savedata.customdata.SaveForm;
import tomorinmod.util.CardStats;

public class DarkTomorin extends BaseMonmentCard {

    public static final String ID = makeID(DarkTomorin.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public DarkTomorin() {
        super(ID, info);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if(!SaveForm.getInstance().getForm().isEmpty()){
            addToBot(new RemoveSpecificPowerAction(p, p, makeID(SaveForm.getInstance().getForm())));
        }

        addToBot(new ApplyPowerAction(p, p, new DarkTomorinPower(p),1));

        SaveForm.getInstance().changeForm("DarkTomorinPower");
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DarkTomorin();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(0); // 将费用从 1 降为 0
        }
    }
}
