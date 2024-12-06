package tomorinmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Shine;
import tomorinmod.powers.StrengthTomorinPower;
import tomorinmod.savedata.SaveForm;
import tomorinmod.savedata.SavePermanentForm;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
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

        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,4),4));
        addToBot(new ApplyPowerAction(p, p, new StrengthTomorinPower(p),1));

        SaveForm.getInstance().changeForm("StrengthTomorinPower");
        AddTagsUtils.addTags(this, CustomTags.MOMENT);
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
