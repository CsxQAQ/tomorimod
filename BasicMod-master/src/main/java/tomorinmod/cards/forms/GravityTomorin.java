package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.monitors.HandleFormsMonitor;
import tomorinmod.powers.forms.GravityTomorinPower;
import tomorinmod.savedata.customdata.SaveForm;
import tomorinmod.util.CardStats;

public class GravityTomorin extends BaseFormCard {


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
        setFormPower();
    }

    @Override
    public void setFormPower(){
        formPower="GravityTomorinPower";
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(p, p, new GravityTomorinPower(p),1));

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
