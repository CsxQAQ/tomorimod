package tomorinmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.WeAreMygoPower;
import tomorinmod.savedata.SavePermanentForm;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.AddTagsUtils;
import tomorinmod.util.CardStats;

public class WeAreMygo extends BaseCard {

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

        SavePermanentForm.getInstance().getForms().add("WeAreMygoPower");
        AddTagsUtils.addTags(this, CustomTags.MOMENT);

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
