package tomorinmod.cards.commom;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.actions.CheckShineGravityAction;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.GravityTomorinPower;
import tomorinmod.powers.StrengthTomorinPower;
import tomorinmod.savedata.SaveForm;
import tomorinmod.savedata.SavePermanentForm;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

public class GravityTomorin extends BaseCard {


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

        addToBot(new ApplyPowerAction(p,p,new Gravity(p,4),4));
        addToBot(new CheckShineGravityAction(p));

        addToBot(new ApplyPowerAction(p, p, new GravityTomorinPower(p),1));

        if (AbstractDungeon.player instanceof MyCharacter) {
            SaveForm.getInstance().changeForm("GravityTomorinPower");
            AddTagsUtils.addTags(this, CustomTags.MOMENT);
        }
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
