package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.StrengthTomorinPower;
import tomorinmod.savedata.SaveForm;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.CustomUtils;
import tomorinmod.util.CardStats;

public class StrengthTomorin extends BaseCard {

    public static final String ID = makeID(StrengthTomorin.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public StrengthTomorin() {
        super(ID, info);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if(!SaveForm.getInstance().getForm().equals("")){
            addToBot(new RemoveSpecificPowerAction(p, p, makeID(SaveForm.getInstance().getForm())));
        }

        addToBot(new ApplyPowerAction(p, p, new StrengthTomorinPower(p),1));

        SaveForm.getInstance().changeForm("StrengthTomorinPower");
        CustomUtils.addTags(this, CustomTags.MOMENT);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new StrengthTomorin();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(0); // 将费用从 1 降为 0
        }
    }
}
