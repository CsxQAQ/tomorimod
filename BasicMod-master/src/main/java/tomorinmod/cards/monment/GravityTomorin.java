package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.GravityTomorinPower;
import tomorinmod.savedata.customdata.SaveForm;
import tomorinmod.util.CardStats;

public class GravityTomorin extends BaseMonmentCard {


    public static final String ID = makeID(GravityTomorin.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public GravityTomorin() {
        super(ID, info);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!SaveForm.getInstance().getForm().equals("")){
            addToBot(new RemoveSpecificPowerAction(p, p, makeID(SaveForm.getInstance().getForm())));
        }

        addToBot(new ApplyPowerAction(p, p, new GravityTomorinPower(p),1));

        SaveForm.getInstance().changeForm("GravityTomorinPower");
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        super.use(p,m);

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GravityTomorin();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(0); // 将费用从 1 降为 0
        }
    }
}
