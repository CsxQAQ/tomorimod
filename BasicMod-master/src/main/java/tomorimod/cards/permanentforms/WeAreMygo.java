package tomorimod.cards.permanentforms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.WeAreMygoPower;
import tomorimod.savedata.customdata.PermanentFormsSaveData;
import tomorimod.util.CardStats;

import static tomorimod.util.CustomUtils.idToName;

public class WeAreMygo extends BaseCard implements PermanentFrom{

    public static final String ID = makeID(WeAreMygo.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );


    public WeAreMygo() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WeAreMygoPower(p)));
        PermanentFormsSaveData.getInstance().addPermanentForms(idToName(ID));
        BaseMonmentCard.removeFromMasterDeck(this);

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WeAreMygo();
    }
}
